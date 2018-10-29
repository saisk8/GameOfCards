package GameOfCards.Game.DumbGame;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import GameOfCards.Basics.*;
import java.io.*;
import java.util.List;

public class Dumb {
    private Deck cardDeck;
    private DumbGameHand[] playerHands;
    private final int NUMBER_OF_PLAYERS;
    private Socket[] players;

    public Dumb(Socket[] players, int NUMBER_OF_PLAYERS) {
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

    public void startGame() {
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
        List<Integer> scores = new ArrayList<Integer>();
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            scores.add(playerHands[i].evaluateHand());
        }
        Comms.declareWinner(outStream, Collections.max(scores), false, NUMBER_OF_PLAYERS);
        System.out.println("Game ended!");
    }

}
