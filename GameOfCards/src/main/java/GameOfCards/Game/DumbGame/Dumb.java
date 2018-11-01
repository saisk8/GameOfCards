package GameOfCards.Game.DumbGame;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import GameOfCards.Basics.*;
import java.io.*;
import java.util.List;

/**
 * The class that implements the Dumn game rules
 */
public class Dumb {
    private Deck cardDeck;
    private DumbGameHand[] playerHands;
    private final int NUMBER_OF_PLAYERS;
    private Socket[] players;

    /**
     * Creates a new Dealer for the dumb game
     * 
     * @param players
     * @param NUMBER_OF_PLAYERS
     */
    public Dumb(Socket[] players, int NUMBER_OF_PLAYERS) {
        this.players = players;
        this.NUMBER_OF_PLAYERS = NUMBER_OF_PLAYERS;
    }

    /**
     * The method to initialise the game
     */
    public void init() {
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
        playerHands = new DumbGameHand[NUMBER_OF_PLAYERS];
        for (int i = 0; i < playerHands.length; i++) {
            playerHands[i] = new DumbGameHand();
        }
        int index = 0;
        while (!cardDeck.isEmpty()) {
            Card drawnCard = cardDeck.dealCard();
            playerHands[index].addCard(drawnCard);
            index = (index + 1) % NUMBER_OF_PLAYERS;
        }
    }

    /**
     * The method that runs the game.
     */
    public void startGame() {
        ObjectOutputStream[] outStream = new ObjectOutputStream[NUMBER_OF_PLAYERS];
        try {
            for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
                outStream[i] = new ObjectOutputStream(players[i].getOutputStream());
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        List<Integer> scores = new ArrayList<Integer>();
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            scores.add(playerHands[i].evaluateHand());
            String socreStr = "Your score is: " + scores.get(i);
            Comms.sendData(outStream[i], socreStr);
        }
        Comms.declareWinner(outStream, scores.indexOf(Collections.max(scores)), false,
                NUMBER_OF_PLAYERS);
        System.out.println("Game ended!");
    }

}
