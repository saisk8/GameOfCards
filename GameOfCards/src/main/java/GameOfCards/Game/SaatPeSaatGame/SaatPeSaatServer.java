package GameOfCards.Game.SaatPeSaatGame;

import java.net.*;
import java.io.*;

public class SaatPeSaatServer {

    private final static int PORT = 3000;
    static int thePort;
    static int numberOfPlayers;

    public SaatPeSaatServer(int groupId, int number) {
        thePort += PORT + groupId;
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
                if (index == numberOfPlayers) {
                    break;
                }
            }
            SaatPeSaat sps = new SaatPeSaat(sockets, numberOfPlayers);
            sps.init();
            sps.startGame();
        } catch (IOException e) {
            System.err.println("Server aborted prematurely");
        }
    }
}
