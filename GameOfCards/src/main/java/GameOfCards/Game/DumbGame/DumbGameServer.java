package GameOfCards.Game.DumbGame;

import java.net.*;
import java.io.*;

/**
 * Implementation of the dumb game dedicated server.
 */
public class DumbGameServer {

    private final static int PORT = 3000;
    static int thePort;
    static int numberOfPlayers;

    /**
     * To create a new dumb game dedicated server
     * 
     * @param groupId The group ID decided
     * @param number  The number of players
     */
    public DumbGameServer(int groupId, int number) {
        thePort = PORT + groupId;
        numberOfPlayers = number;
    }

    /**
     * The method to intialise the game
     */
    public void init() {
        ServerSocket listeningSocket;
        try {
            int index = 0;
            Socket sockets[] = new Socket[numberOfPlayers];
            listeningSocket = new ServerSocket(thePort);
            System.out.println("Accepting connections on port " + listeningSocket.getLocalPort());
            while (true) {
                sockets[index++] = listeningSocket.accept();
                System.out.println("Number of players expected at " + listeningSocket.getLocalPort()
                        + " is/are: " + (numberOfPlayers - index));
                if (index == numberOfPlayers) {
                    break;
                }
            }
            Dumb dumb = new Dumb(sockets, numberOfPlayers);
            dumb.init();
            dumb.startGame();
        } catch (IOException e) {
            System.err.println("Server aborted prematurely " + thePort);
        }
    }
}
