package GameOfCards.Game.SaatPeSaatGame;

import java.io.*;
import java.net.Socket;
import GameOfCards.Basics.*;

public class SaatPeSaatClient {
    Socket player;

    public SaatPeSaatClient(Socket player) {
        this.player = player;
    }

    public void playGame() {
        SaatHand hand = null;
        ObjectInputStream inStream = null;
        ObjectOutputStream outStream = null;
        try {
            outStream = new ObjectOutputStream(player.getOutputStream());
            inStream = new ObjectInputStream(player.getInputStream());

        } catch (IOException io) {
            System.out.println("Error...");
        }
        do {
            System.out.println("Wait for turn...");
            try {

                hand = (SaatHand) inStream.readObject();
            } catch (IOException io) {
                io.printStackTrace();
                System.out.println("Comms Receive error: IO");
            } catch (ClassNotFoundException cnf) {
                System.out.println("Comms Receive error: Class not found");
            }
            System.out.println("Your turn..." + hand.TURN);
            if (hand != null) {
                System.out.println("Heer");
                System.out.println("Your hand: " + hand);
                System.out.println("Card on top: " + hand.getCardOnTop());
                hand.setAction();
                try {
                    outStream.writeObject(hand);
                } catch (IOException io) {
                    io.printStackTrace();
                    System.out.println("Comms Send error: IO");
                }
                hand.TURN = false;
            }
        } while (hand != null && hand.isGameRunning());
        if (hand.isWinner()) {
            System.out.println("You Win!");
        } else {
            System.out.println("You lose!");
        }
    }
}
