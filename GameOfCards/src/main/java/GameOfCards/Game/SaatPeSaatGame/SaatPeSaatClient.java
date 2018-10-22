package GameOfCards.Game.SaatPeSaatGame;

import java.net.Socket;
import GameOfCards.Basics.*;

public class SaatPeSaatClient {
    Socket player;

    public SaatPeSaatClient(Socket player) {
        this.player = player;
    }

    public void playGame() {
        SaatHand hand = null;
        do {
            if (Comms.receiveTurnAlert(player)) {
                hand = (SaatHand) Comms.receiveData(player);
                System.out.println("Card on top: " + hand.getCardOnTop());
                hand.setAction();
                Comms.sendData(player, hand);
            }
        } while (hand != null && hand.isGameRunning());
        if (hand.isWinner()) {
            System.out.println("You Win!");
        } else {
            System.out.println("You Win!");
        }
    }
}
