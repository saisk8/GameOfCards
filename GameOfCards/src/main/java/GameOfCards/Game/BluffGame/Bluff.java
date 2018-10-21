package GameOfCards.Game.BluffGame;

import java.util.Iterator;
import java.net.*;
import GameOfCards.Basics.*;

public class Bluff {
    private Deck cardDeck, discardDeck;
    private BluffHand[] playerHand;
    private final int INITIAL_SIZE_OF_HAND = 8;
    private final int NUMBER_OF_PLAYERS;
    private Socket[] players;
    private boolean bluffed = false;
    private String lastAction = "";
    // private Socket delear;

    public Bluff(Socket[] players, int NUMBER_OF_PLAYERS) {
        this.players = players;
        // this.delear = delear;
        this.playerHand = new BluffHand[NUMBER_OF_PLAYERS];
        this.NUMBER_OF_PLAYERS = NUMBER_OF_PLAYERS;
    }

    public void init() {
        // add the Card instantiations here
        cardDeck = new Deck();
        discardDeck = new Deck();
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
        // Change this....
        for (int j = 0; j < INITIAL_SIZE_OF_HAND; j++) {
            for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
                Card drawnCard = cardDeck.dealCard();
                playerHand[i].addCard(drawnCard);
            }
        }
    }

    public void startGame() {
        boolean NO_WINNER = true;
        int turn = 0, prev = -1;
        BluffActions action = new BluffActions();
        while (NO_WINNER) {
            // Happenings of a single turn until, one of the player emerges as the winner
            Comms.alertPlayerTurn(players[turn]);
            Comms.sendData(players[turn], playerHand[turn]);
            action = (BluffActions) Comms.receiveData(players[turn]);
            // Perform actions
            int option = action.getOption();
            switch (option) {
                case 1:
                    if (!bluffed) {
                        Comms.sendAck(players[turn], "Success");
                        for (int i = 0; i < discardDeck.getSizeOfDeck(); i++) {
                            playerHand[prev].addCard(discardDeck.dealCard());
                        }
                        discardDeck.restoreDeck();
                        prev = turn;
                        turn = ((turn - 1) + NUMBER_OF_PLAYERS) % NUMBER_OF_PLAYERS;
                    } else {
                        Comms.sendAck(players[turn], "Fail");
                        for (int i = 0; i < discardDeck.getSizeOfDeck(); i++) {
                            playerHand[prev].addCard(discardDeck.dealCard());
                        }
                        discardDeck.restoreDeck();
                        turn = prev;
                    }
                    break;
                case 2:
                    turn += 1;
                    break;
                case 3:
                    boolean bTemp = false;
                    lastAction = action.lastAction();
                    String[] strs = lastAction.trim().split("\\s+");
                    int[] indices = action.getIndices();
                    for (int i = 0; i < indices.length; i++) {
                        Card temp = playerHand[turn].removeCard(indices[i]);
                        if (!temp.getRank().equals(strs[1])) {
                            bTemp = true;
                        }
                        discardDeck.addCard(temp);
                    }
                    bluffed = bTemp;
                    prev = turn;
                    turn = (turn + 1) % NUMBER_OF_PLAYERS;
            }
            NO_WINNER = checkWinner();
            // prev = turn;
            // turn = (turn + 1) % NUMBER_OF_PLAYERS;
        }
        Comms.declareWinner(players, turn);
    }

    public void performAction(Socket[] players, BluffHand[] hands, BluffActions action, int curr,
            int prev) {

    }

}