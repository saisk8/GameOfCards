package GameOfCards.Game.DumbGame;

import java.net.*;
import java.io.*;

public class DumbGameServer {

    private final static int PORT = 3000;
    static int thePort;
    static int numberOfPlayers;

    public DumbGameServer(int groupId, int number) {
        thePort = PORT + groupId;
        numberOfPlayers = number;
    }

    public void init() {
        ServerSocket listeningSocket;
        try {
            int index = 0;
            Socket sockets[] = new Socket[numberOfPlayers];
            listeningSocket = new ServerSocket(thePort);
            System.out.println("Accepting connections on port " + listeningSocket.getLocalPort());
            while (true) {
                sockets[index++] = listeningSocket.accept();
                System.out.println("Number of players expected at " + listeningSocket.getLocalPort() + " is/are: "
                        + (numberOfPlayers - index));
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
