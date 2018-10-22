package GameOfCards.Game.SaatPeSaatGame;

import java.net.Socket;
import java.util.Iterator;
import GameOfCards.Basics.*;

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
        while (NO_WINNER) {
            // Happenings of a single turn until, one of the player emerges as the winner
            Comms.alertPlayerTurn(players[turn]);
            Comms.sendData(players[turn], playerHands[turn]);
            action = (SaatHand) Comms.receiveData(players[turn]);
            int next = (turn + 1) % NUMBER_OF_PLAYERS;
            if (action.getAction() == -1) {
                turn = next;
                continue;
            }
            NO_WINNER = !playerHands[turn].isEmpty();
            if (NO_WINNER) {
                playerHands[next].setGameStatus(NO_WINNER);
                playerHands[next].setCardOnTop(playerHands[turn].removeCard(action.getAction()));
                break;
            }
            turn = next;
        }
        declareWinner(players, turn);
    }

    public void declareWinner(Socket[] players, int turn) {
        int index = turn;
        playerHands[index].setGameStatus(false);
        playerHands[index].setWinner(false);
        Comms.sendData(players[index], playerHands[index]);
        do {
            index = (index + 1) % NUMBER_OF_PLAYERS;
            playerHands[turn].setGameStatus(false);
            playerHands[turn].setWinner(false);
            Comms.sendData(players[index], playerHands[index]);
        } while (index != turn);
    }

}
