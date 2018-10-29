package GameOfCards.Basics;

import java.io.Serializable;
import java.util.*;

public class Deck implements Serializable {
    private java.util.List<Card> deck;
    private int index;
    private final static long serialVersionUID = 1;


    /**
     * Creates an empty deck of cards.
     */
    public Deck() {
        deck = new ArrayList<Card>();
        index = 0;
    }


    /**
     * Adds a card to the deck.
     * 
     * @param card card to be added to the deck.
     */
    public void addCard(Card card) {
        deck.add(card);
    }


    /**
     * The size of a deck of cards.
     * 
     * @return the number of cards present in the full deck.
     */
    public int getSizeOfDeck() {
        return deck.size();
    }


    /**
     * The number of cards left in the deck.
     * 
     * @return the number of cards left to be dealt from the deck.
     */
    public int getNumberOfCardsRemaining() {
        return deck.size() - index;
    }


    /**
     * Deal one card from the deck.
     * 
     * @return a card from the deck, or the null reference if there are no cards left in the deck.
     */
    public Card dealCard() {
        if (index >= deck.size())
            return null;
        else
            return (Card) deck.get(index++);
    }


    /**
     * Shuffles the cards present in the deck.
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }


    /**
     * Looks for an empty deck.
     * 
     * @return <code>true</code> if there are no cards left to be dealt from the deck.
     */
    public boolean isEmpty() {
        if (index >= deck.size())
            return true;
        else
            return false;
    }


    /**
     * Restores the deck to "full deck" status.
     */
    public void restoreDeck() {
        deck = new ArrayList<Card>();
        index = 0;
    }

}
