package GameOfCards.Game.DumbGame;

import GameOfCards.Basics.*;

/**
 * Implementation of the rules for the dumb card game.
 */
public class DumbGameHand extends Hand {
    private final static long serialVersionUID = 1;

    /**
     * Creates a hand for the dumb card game.
     */
    public DumbGameHand() {
        super();
    }

    /**
     * Evaluates a hand according to the rules of the dumb card game. Each card is worth its
     * displayed pip value (ace = 1, two = 2, etc.) in points with face cards worth ten points. The
     * value of a hand is equal to the summation of the points of all the cards held in the hand.
     */
    public int evaluateHand() {
        int value = 0;
        Rank.setKingHigh();
        for (int i = 0; i < getNumberOfCards(); i++) {
            Card c = getCard(i);
            int cardValue = c.getRank().compareTo(Rank.ACE) + 1;
            if (cardValue > 10)
                cardValue = 10;
            value += cardValue;
        }

        return value;
    }
}
