package GameOfCards.Game.SaatPeSaatGame;

import java.net.Socket;
import java.util.Iterator;
import GameOfCards.Basics.*;
import java.io.*;
import java.util.List;

public class SaatPeSaat {
    private Deck cardDeck;
    private SaatHand[] playerHands;
    private int action;
    private final int NUMBER_OF_PLAYERS;
    private Socket[] players;
    private int firstPlayer = -1;
    private final Card hearts7 = new Card(Suit.HEARTS, Rank.SEVEN);

    public SaatPeSaat(Socket[] players, int NUMBER_OF_PLAYERS) {
        this.players = players;
        this.NUMBER_OF_PLAYERS = NUMBER_OF_PLAYERS;
    }

    public void init() {
        // add the Card instantiations here
        cardDeck = new Deck();
        Iterator suitIterator = Suit.VALUES.iterator();
        while (suitIterator.hasNext()) {
            Suit suit = (Suit) suitIterator.next();
            Iterator rankIterator = Rank.VALUES.iterator();
            while (rankIterator.hasNext()) {
                Rank rank = (Rank) rankIterator.next();
                Card card = new Card(suit, rank);
                cardDeck.addCard(card);
            }
        }
        cardDeck.shuffle();
        // set up the initial hand
        playerHands = new SaatHand[NUMBER_OF_PLAYERS];
        for (int i = 0; i < playerHands.length; i++) {
            playerHands[i] = new SaatHand();
        }
        int index = 0;
        while (!cardDeck.isEmpty()) {
            Card drawnCard = cardDeck.dealCard();
            if (drawnCard.compareTo(hearts7) == 0) {
                firstPlayer = index;
            }
            playerHands[index].addCard(drawnCard);
            index = (index + 1) % NUMBER_OF_PLAYERS;
        }
    }

    public void startGame() {
        boolean NO_WINNER = true;
        int count = 0;
        int turn = (firstPlayer + 1) % NUMBER_OF_PLAYERS;
        playerHands[turn].setCardOnTop(hearts7);
        DataInputStream[] inStream = new DataInputStream[NUMBER_OF_PLAYERS];
        ObjectOutputStream[] outStream = new ObjectOutputStream[NUMBER_OF_PLAYERS];
        try {
            for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
                inStream[i] = new DataInputStream(players[i].getInputStream());
                outStream[i] = new ObjectOutputStream(players[i].getOutputStream());
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        System.out.println(turn);
        while (NO_WINNER) {
            count++;
            String actionStr = "Your turn...\n" + "Your Hand: " + playerHands[turn] + "\n"
                    + "Card on top: " + playerHands[turn].getCardOnTop() + "\n";
            List<Integer> options = playerHands[turn].evaluateOptions();
            if (options.size() == 0) {
                actionStr += ("No possible plays...");
            } else {
                actionStr += "Possible plays: " + options.toString() + "\n";
                actionStr += "Select an option: ";
            }
            try {
                outStream[turn].writeObject(actionStr);
            } catch (IOException io) {
                io.printStackTrace();
            }

            try {
                action = inStream[turn].readInt();
            } catch (IOException io) {
                io.printStackTrace();
            }
            int next = (turn + 1) % NUMBER_OF_PLAYERS;
            if (action == -1) {
                System.out.println("Here1");
                playerHands[next].setCardOnTop(playerHands[turn].getCardOnTop());
                turn = next;
                if (count > 3 && checkLoop()) {
                    System.out.println("Infinite loop");
                    NO_WINNER = false;
                }
                continue;
            } else if (action == -2) {
                System.out.println("An unknown error occured...");
                return;
            }
            NO_WINNER = !playerHands[turn].isEmpty();
            if (NO_WINNER) {
                System.out.println(turn + " " + next + " " + action);
                playerHands[next].setCardOnTop(playerHands[turn].removeCard(action));
            }
            turn = next;
        }

        declareWinner(outStream, turn);
        System.out.println("Game ended!");
    }

    public void declareWinner(ObjectOutputStream[] outds, int turn) {
        int index = turn;
        if (checkLoop()) {
            do {
                try {
                    outds[index].writeObject("You Lose!\n\n");
                    outds[index].close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
                index = (index + 1) % NUMBER_OF_PLAYERS;
            } while (index != turn);
            return;
        }
        try {
            outds[index].writeObject("You Win!\n\n");
            outds[index].close();
        } catch (IOException io) {
            io.printStackTrace();
        }
        do {
            try {
                outds[index].writeObject("You Lose!\n\n");
                outds[index].close();
            } catch (IOException io) {
                io.printStackTrace();
            }
            index = (index + 1) % NUMBER_OF_PLAYERS;
        } while (index != turn);
    }

    public boolean checkLoop() {
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            if (!playerHands[i].evaluateOptions().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
