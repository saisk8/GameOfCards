package GameOfCards.Game.SaatPeSaatGame;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import GameOfCards.Basics.*;

/**
 * Implementation of the Saat Pe Saat game's player hand
 */
public class SaatHand extends Hand implements Serializable {
    private static final long serialVersionUID = 1;
    private Card cardOnTop;
    private int action;

    public SaatHand() {
        super();
    }

    /**
     * To set the card played by the last user
     * 
     * @param card The last player Card
     */
    public void setCardOnTop(Card card) {
        cardOnTop = card;
    }

    /**
     * Get the last played card
     * 
     * @return The last played Card's object
     */
    public Card getCardOnTop() {
        return cardOnTop;
    }

    /**
     * A method that helps the user decide his options
     * 
     * @return The List of options available
     */
    public List<Card> evaluateOptions() {
        Rank rankValue = cardOnTop.getRank();
        Suit suitValue = cardOnTop.getSuit();
        List<Card> possible = new ArrayList<Card>();
        for (int i = 0; i < getNumberOfCards(); i++) {
            if (rankValue.getName().equals(getCard(i).getRank().getName())) {
                possible.add(getCard(i));
                continue;
            } else if (suitValue.getName().equals(getCard(i).getSuit().getName())) {
                if (rankValue.compareTo(getCard(i).getRank()) == -1
                        || rankValue.compareTo(getCard(i).getRank()) == 1) {
                    possible.add(getCard(i));
                    continue;
                }
            }
        }
        return possible;
    }

    /**
     * To get the value of action variable
     * 
     * @return The value of action
     */
    public int getAction() {
        return action;
    }
}
