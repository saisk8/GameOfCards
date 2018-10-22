package GameOfCards.Game.SaatPeSaatGame;

import java.net.Socket;
import java.util.Iterator;
import GameOfCards.Basics.*;
import java.io.*;

public class SaatPeSaat {
    private Deck cardDeck;
    private SaatHand[] playerHands;
    private SaatHand action;
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
        int turn = (firstPlayer + 1) % NUMBER_OF_PLAYERS;
        playerHands[turn].setCardOnTop(hearts7);
        playerHands[turn].setGameStatus(true);
        playerHands[turn].setWinner(false);
        playerHands[turn].TURN = true;
        ObjectOutputStream[] outStream = new ObjectOutputStream[NUMBER_OF_PLAYERS];
        ObjectInputStream[] inStream = new ObjectInputStream[NUMBER_OF_PLAYERS];
        try {
            for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
                inStream[i] = new ObjectInputStream(players[i].getInputStream());
                outStream[i] = new ObjectOutputStream(players[i].getOutputStream());
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        System.out.println(turn);
        while (NO_WINNER) {
            try {
                outStream[turn].writeObject(playerHands[turn]);
            } catch (IOException io) {
                io.printStackTrace();
                System.out.println("Send error: IO");
            }
            action = new SaatHand();
            // action = (SaatHand) Comms.receiveData(players[turn]);
            try {
                action = (SaatHand) inStream[turn].readObject();
            } catch (IOException io) {
                io.printStackTrace();
                System.out.println("Comms Receive error: IO");
            } catch (ClassNotFoundException cnf) {
                System.out.println("Comms Receive error: Class not found");
            }
            int next = (turn + 1) % NUMBER_OF_PLAYERS;
            if (action.getAction() == -1) {
                System.out.println("Here1");
                playerHands[next].setGameStatus(NO_WINNER);
                playerHands[next].setCardOnTop(playerHands[turn].getCardOnTop());
                playerHands[next].TURN = true;
                turn = next;
                continue;
            }
            NO_WINNER = !playerHands[turn].isEmpty();
            if (NO_WINNER) {
                System.out.println(turn + " " + next);
                playerHands[next].setGameStatus(NO_WINNER);
                playerHands[next].setCardOnTop(playerHands[turn].removeCard(action.getAction()));
                playerHands[next].TURN = true;
            }
            turn = next;
        }
        declareWinner(players, turn);
    }

    public void declareWinner(Socket[] players, int turn) {
        int index = turn;
        playerHands[index].setGameStatus(false);
        playerHands[index].setWinner(true);
        Comms.sendData(players[index], playerHands[index]);
        do {
            index = (index + 1) % NUMBER_OF_PLAYERS;
            playerHands[turn].setGameStatus(false);
            playerHands[turn].setWinner(false);
            Comms.sendData(players[index], playerHands[index]);
        } while (index != turn);
    }

}
