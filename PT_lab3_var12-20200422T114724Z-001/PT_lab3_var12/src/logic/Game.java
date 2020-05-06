package logic;

import logic.exceptions.DeckIndexException;
import logic.exceptions.GameOverException;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Game {
    /**
     * Количество карт в колоде
     */
    private static final int CARDS_IN_DECK = 15;

    /**
     * Карты на руках
     */
    public static final int ARM_CARDS = 5;

    private Deck myDeck;
    private Deck arm;

    private Connection connection;

    private Scanner sc;

    /**
     * Количество карт у противника
     */
    private int opponentCount;

    public Game(Connection connection) {
        this.connection = connection;
        Random r = new Random();
        myDeck = new Deck();
        arm = new Deck();
        for (int i = 0; i < CARDS_IN_DECK; i++) {
            // заполнение колоды распределением гаусса с мат ожиданием = 0, СКО = 3
            int card;
            // нулевых карт быть не должно!
            do{
                card = (int)(r.nextGaussian()*3);
            }while (card == 0);
            myDeck.addCard(card);
        }
        arm = myDeck.removeSubDeck(ARM_CARDS);
        sc = new Scanner(System.in);
        opponentCount = myDeck.size();
    }

    /**
     * Мой ход
     */
    public void myMove() throws GameOverException, IOException {
        System.out.println("У вас " + myDeck.size() + " карт в колоде");
        if (myDeck.size() == 0){
            throw new GameOverException("Игра завершена");
        }
        System.out.println("Выберите карту");
        System.out.println(arm);
        System.out.print(">>> ");
        int i = sc.nextInt();

        if (i  > arm.size()){
            i = arm.size() - 1;
        }

        int card = arm.remove(i);

        // отправляем карту
        connection.sendInt(card);

        myDeck.addCardToStart(card);

        if (card > 0){
            // получаем карты
            myDeck.addDeck(connection.readDeck());
        }else {
            // отправляем карты
            connection.sendDeck(myDeck.removeSubDeck(abs(card)));
        }
        try {
            arm.addCard(myDeck.removeLast());
        } catch (DeckIndexException ignored) {}

        // оптправляем количество карт в конце хода
        connection.sendInt(myDeck.size());

        opponentCount -= card;

        if (myDeck.size() == 0){
            throw new GameOverException("Игра завершена");
        }

    }

    /**
     * Ход противника
     */
    public void opponentMove() throws GameOverException, IOException {
        if (opponentCount <= 0){
            throw new GameOverException("Игра завершена");
        }
        System.out.println("### Ожидание хода противника ###");
        // получаем информацию о том сколько карт и куда перемещать
        int card = connection.readInt();

        if (card > 0){
            // отправляем карты
            connection.sendDeck(myDeck.removeSubDeck(card));
        }else {
            // получаем карты
            myDeck.addDeck(connection.readDeck());
        }

        opponentCount = connection.readInt();
        System.out.println("У противника " + opponentCount + " карт в колоде");
        if (opponentCount == 0){
            throw new GameOverException("Игра завершена");
        }
    }


    public void run() throws IOException {
        while (true){
            try {
                opponentMove();
            } catch (GameOverException e) {
                System.out.println("Игра завершена, вы победили");
                break;
            }
            try {
                myMove();
            } catch (GameOverException e) {
                System.out.println("Игра завершена, вы проиграли");
                break;
            }

        }
    }
}
