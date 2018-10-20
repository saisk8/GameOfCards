package GameOfCards.Basics;

import java.util.*;

public abstract class Hand implements Comparable {

    private java.util.List hand = new ArrayList();


    /**
     * Adds a card to this hand.
     * 
     * @param card card to be added to the current hand.
     */
    public void addCard(Card card) {
        hand.add(card);
    }


    /**
     * Obtains the card stored at the specified location in the hand. Does not remove the card from
     * the hand.
     * 
     * @param index position of card to be accessed.
     * @return the card of interest, or the null reference if the index is out of bounds.
     */
    public Card getCard(int index) {
        return (Card) hand.get(index);
    }


    /**
     * Removes the specified card from the current hand.
     * 
     * @param card the card to be removed.
     * @return the card removed from the hand, or null if the card was not present in the hand.
     */
    public Card removeCard(Card card) {
        int index = hand.indexOf(card);
        if (index < 0)
            return null;
        else
            return (Card) hand.remove(index);
    }


    /**
     * Removes the card at the specified index from the hand.
     * 
     * @param index poisition of the card to be removed.
     * @return the card removed from the hand, or the null reference if the index is out of bounds.
     */
    public Card removeCard(int index) {
        return (Card) hand.remove(index);
    }


    /**
     * Removes all the cards from the hand, leaving an empty hand.
     */
    public void discardHand() {
        hand.clear();
    }


    /**
     * The number of cards held in the hand.
     * 
     * @return number of cards currently held in the hand.
     */
    public int getNumberOfCards() {
        return hand.size();
    }


    /**
     * Sorts the card in the hand. Sort is performed according to the order specified in the
     * {@link Card} class.
     */
    public void sort() {
        Collections.sort(hand);
    }


    /**
     * Checks to see if the hand is empty.
     * 
     * @return <code>true</code> is the hand is empty.
     */
    public boolean isEmpty() {
        return hand.isEmpty();
    }


    /**
     * Determines whether or not the hand contains the specified card.
     * 
     * @param card the card being searched for in the hand.
     * @return <code>true</code> if the card is present in the hand.
     */
    public boolean containsCard(Card card) {
        return false;
    }


    /**
     * Searches for the first instance of the specified card in the hand.
     * 
     * @param card card being searched for.
     * @return position index of card if found, or <code>-1</code> if not found.
     */
    public int findCard(Card card) {
        return hand.indexOf(card);
    }


    /**
     * Compares two hands.
     * 
     * @param otherHandObject the hand being compared.
     * @return < 0 if this hand is less than the other hand, 0 if the two hands are the same, or > 0
     *         if this hand is greater then the other hand.
     */
    public int compareTo(Object otherHandObject) {
        Hand otherHand = (Hand) otherHandObject;
        return evaluateHand() - otherHand.evaluateHand();
    }


    /**
     * Evaluates the hand. Must be defined in the subclass that implements the hand for the game
     * being written by the client programmer.
     * 
     * @return an integer corresponding to the rating of the hand.
     */
    public abstract int evaluateHand();


    /**
     * Returns a description of the hand.
     * 
     * @return a list of cards held in the hand.
     */
    public String toString() {
        return hand.toString();
    }


    /**
     * Replaces the specified card with another card. Only the first instance of the targeted card
     * is replaced. No action occurs if the targeted card is not present in the hand.
     * 
     * @return <code>true</code> if the replacement occurs.
     */
    public boolean replaceCard(Card oldCard, Card replacementCard) {
        int location = findCard(oldCard);
        if (location < 0)
            return false;
        hand.set(location, replacementCard);
        return true;
    }

}
