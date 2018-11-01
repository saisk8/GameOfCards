package GameOfCards.Game.SaatPeSaatGame;

import java.net.Socket;
import java.util.Iterator;
import GameOfCards.Basics.*;
import java.io.*;
import java.util.List;

/**
 * A class that implements the Saat pe Saat game's rules
 */
public class SaatPeSaat {
    private Deck cardDeck;
    private SaatHand[] playerHands;
    private int action;
    private final int NUMBER_OF_PLAYERS;
    private Socket[] players;
    private int firstPlayer = -1;
    private final Card hearts7 = new Card(Suit.HEARTS, Rank.SEVEN);

    /**
     * The constructor of Saat pe Saat game
     * 
     * @param players           The array of Sockets of the players playing the game
     * @param NUMBER_OF_PLAYERS The number of players in the game
     */
    public SaatPeSaat(Socket[] players, int NUMBER_OF_PLAYERS) {
        this.players = players;
        this.NUMBER_OF_PLAYERS = NUMBER_OF_PLAYERS;
    }

    /**
     * A method to intialise the game
     */
    public void init() {
        // System.out.println("In Init");
        // add the Card instantiations here
        cardDeck = new Deck();
        Iterator<Suit> suitIterator = Suit.VALUES.iterator();
        while (suitIterator.hasNext()) {
            Suit suit = (Suit) suitIterator.next();
            Iterator<Rank> rankIterator = Rank.VALUES.iterator();
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

    /**
     * The method to start the game
     */
    public void startGame() {
        // System.out.println("In start");
        boolean NO_WINNER = true;
        int count = 0;
        int turn = (firstPlayer + 1) % NUMBER_OF_PLAYERS;
        playerHands[turn].setCardOnTop(hearts7);
        ObjectInputStream[] inStream = new ObjectInputStream[NUMBER_OF_PLAYERS];
        ObjectOutputStream[] outStream = new ObjectOutputStream[NUMBER_OF_PLAYERS];
        try {
            for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
                System.out.print(i);
                outStream[i] = new ObjectOutputStream(players[i].getOutputStream());
                inStream[i] = new ObjectInputStream(players[i].getInputStream());
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        while (NO_WINNER) {
            System.out.println(turn);
            count++;
            String actionStr = "Your turn...\n\n" + "Cards currently in your hand: \n"
                    + playerHands[turn] + "\n\n" + "Last played card: "
                    + playerHands[turn].getCardOnTop() + "\n\n";
            List<Card> options = playerHands[turn].evaluateOptions();
            if (options.size() == 0) {
                actionStr += ("No possible plays...");
            } else {
                actionStr += "You can play the following cards from your hand:\n " + options + "\n";
                actionStr +=
                        "Please enter the index from the list of possible plays(from 1 to n): ";
            }
            Comms.sendData(outStream[turn], actionStr);
            action = (Integer) Comms.receiveData(inStream[turn]);
            int next = (turn + 1) % NUMBER_OF_PLAYERS;
            if (action == -1) {
                // System.out.println("Here1");
                playerHands[next].setCardOnTop(playerHands[turn].getCardOnTop());
                turn = next;
                if (count > 3 && checkLoop()) {
                    // System.out.println("Infinite loop");
                    NO_WINNER = false;
                }
                continue;
            } else if (action <= -2) {
                System.err.println("An unknown error occured...");
                return;
            }
            NO_WINNER = !playerHands[turn].isEmpty();
            if (NO_WINNER) {
                // System.out.println(turn + " " + next + " " + action);
                playerHands[next].setCardOnTop(playerHands[turn].removeCard(action));
            }
            turn = next;
        }

        Comms.declareWinner(outStream, turn, checkLoop(), NUMBER_OF_PLAYERS);
        System.out.println("Game ended!");
    }

    /**
     * A function to check if, no one can win the game anymore
     * 
     * @return A boolean value, true if no one can win or otherwise false
     */
    public boolean checkLoop() {
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            if (!playerHands[i].evaluateOptions().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
