package client;

import logic.Connection;
import logic.Game;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите адрес сервера");
        String ip = scanner.nextLine();

        try {
            Socket s = new Socket(ip, 54321);
            Game g = new Game(new Connection(s));
            g.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
