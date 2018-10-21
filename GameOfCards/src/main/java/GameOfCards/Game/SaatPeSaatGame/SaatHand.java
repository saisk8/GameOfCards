package GameOfCards.Game.SaatPeSaatGame;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import GameOfCards.Basics.*;

public class SaatHand extends Hand implements Serializable {
    private static final long serialVersionUID = 1;
    private Card cardOnTop;

    public SaatHand() {
        super();
    }

    public void setCardOnTop(Card card) {
        cardOnTop = card;
    }

    public String evaluateOptions() {
        Rank rankValue = cardOnTop.getRank();
        Suit suitValue = cardOnTop.getSuit();
        List<Integer> possible = new ArrayList<Integer>();
        for (int i = 0; i < getNumberOfCards(); i++) {
            if (rankValue.getName().equals(getCard(i).getRank().getName())) {
                possible.add(i);
                continue;
            } else if (suitValue.getName().equals(getCard(i).getSuit().getName())) {
                if (rankValue.compareTo(getCard(i).getRank()) == -1
                        || rankValue.compareTo(getCard(i).getRank()) == 1) {
                    possible.add(i);
                    continue;
                }
            }
        }
        return "Possible plays: " + possible.toString();
    }
}
