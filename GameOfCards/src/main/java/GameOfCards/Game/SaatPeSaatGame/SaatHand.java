package GameOfCards.Game.SaatPeSaatGame;

import java.util.List;
import java.util.Scanner;
import java.io.Serializable;
import java.util.ArrayList;
import GameOfCards.Basics.*;

public class SaatHand extends Hand implements Serializable {
    private static final long serialVersionUID = 1;
    private Card cardOnTop;
    private boolean gameStatus;
    private boolean winner;
    private int action;
    public boolean TURN = false;

    public SaatHand() {
        super();
    }

    public void setCardOnTop(Card card) {
        cardOnTop = card;
    }

    public Card getCardOnTop() {
        return cardOnTop;
    }

    public List<Integer> evaluateOptions() {
        Rank rankValue = cardOnTop.getRank();
        Suit suitValue = cardOnTop.getSuit();
        List<Integer> possible = new ArrayList<Integer>();
        for (int i = 0; i < getNumberOfCards(); i++) {
            if (rankValue.getName().equals(getCard(i).getRank().getName())) {
                possible.add(i + 1);
                continue;
            } else if (suitValue.getName().equals(getCard(i).getSuit().getName())) {
                if (rankValue.compareTo(getCard(i).getRank()) == -1
                        || rankValue.compareTo(getCard(i).getRank()) == 1) {
                    possible.add(i + 1);
                    continue;
                }
            }
        }
        return possible;
    }

    public boolean isGameRunning() {
        return gameStatus;
    }

    public void setGameStatus(boolean status) {
        gameStatus = status;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setAction() {
        List<Integer> opt = evaluateOptions();
        System.out.println("Your turn!");
        if (opt.size() == 0) {
            System.out.println("No possible plays");
            action = -1;
            return;
        }
        System.out.println("Possible plays: " + opt.toString());
        System.out.print("Select an option: ");
        Scanner input = new Scanner(System.in);
        if (input.hasNext())
            action = input.nextInt() - 1;
        input.close();
        if (opt.indexOf(action) < 0)
            action = opt.get(0) - 1;
    }

    public int getAction() {
        return action;
    }
}
