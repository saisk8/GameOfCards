package GameOfCards.Game.SaatPeSaatGame;

import java.net.*;
import java.io.*;

/**
 * The class that implements the dedicated Saat pe Saat server.
 */
public class SaatPeSaatServer {

    private final static int PORT = 3000;
    static int thePort;
    static int numberOfPlayers;

    /**
     * The constructor of the dedicated server
     * 
     * @param groupId The group Id decided
     * @param number  The number of players
     */
    public SaatPeSaatServer(int groupId, int number) {
        thePort = PORT + groupId;
        numberOfPlayers = number;
    }

    /**
     * The method that intialises the game
     */
    public void init() {
        ServerSocket listeningSocket;
        try {
            // System.out.println("Port: " + thePort + " Number of Players: " +
            // numberOfPlayers);
            int index = 0;
            Socket sockets[] = new Socket[numberOfPlayers];
            listeningSocket = new ServerSocket(thePort);
            System.out.println("Accepting connections on port " + listeningSocket.getLocalPort()
                    + " IP = " + listeningSocket.getInetAddress().toString());
            while (true) {
                // System.out.println("Again1 " + listeningSocket.getLocalPort());
                sockets[index++] = listeningSocket.accept();
                System.out.println("Number of players expected at " + listeningSocket.getLocalPort()
                        + " is/are: " + (numberOfPlayers - index));
                if (index == numberOfPlayers) {
                    // System.out.println("Not breaking");
                    break;
                }
            }
            SaatPeSaat sps = new SaatPeSaat(sockets, numberOfPlayers);
            sps.init();
            sps.startGame();
        } catch (IOException e) {
            System.err.println("Server aborted prematurely " + thePort);
        }
    }
}
