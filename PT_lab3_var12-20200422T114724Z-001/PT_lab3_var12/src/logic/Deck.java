package logic;

import logic.exceptions.DeckIndexException;

import java.util.ArrayList;

/**
 * Колода карт
 */
public class Deck {
    private final ArrayList<Integer> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public void addCard(int card){
        cards.add(card);
    }

    public ArrayList<Integer> getCards() {
        return cards;
    }

    /**
     * Удаление верхней карты из колоды
     * @return удаленная карта
     */
    public int removeLast() throws DeckIndexException {
        if (cards.isEmpty()){
            throw new DeckIndexException("Колода пуста");
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * Добавление колоды в конец текущей колоды
     * @param deck добавляемая колода
     */
    public void addDeck(Deck deck){
        cards.addAll(deck.cards);
    }

    /**
     * Снятие верхних карт из колоды
     * @param count количество снимаемых карт
     * @return колода, полученная из снятых карт
     */
    public Deck removeSubDeck(int count) {
        if (count > cards.size()){
            count = cards.size();
        }
        int i = cards.size() - count;
        Deck deck = new Deck();

        for (int j = 0; j < count; j++) {
            deck.addCard(cards.remove(i));
        }
        return deck;
    }

    /**
     * Довавение карты в начало колоды
     */
    public void addCardToStart(int card){
        cards.add(0, card);
    }

    public int remove(int index){
        return cards.remove(index);
    }

    public int size(){
        return cards.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            sb.append(i).append(": ").append(cards.get(i)).append('\n');
        }
        return sb.toString();
    }
}
