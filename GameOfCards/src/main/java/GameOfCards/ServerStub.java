package GameOfCards;

import java.net.*;
import java.io.*;
import GameOfCards.Game.SaatPeSaatGame.*;

public class ServerStub extends Thread {

    Socket theConnection;

    public ServerStub(Socket s) {
        theConnection = s;
    }

    public static void main(String[] args) {

        int thePort;
        ServerSocket listeningSocket;

        // set the port to listen on
        try {
            thePort = Integer.parseInt(args[0]);
            if (thePort < 0 || thePort > 65535)
                thePort = 3000;
        } catch (Exception e) {
            thePort = 3000;
        }

        try {
            int index = 0;
            Socket sockets[] = new Socket[3];
            listeningSocket = new ServerSocket(thePort);
            System.out.println("Accepting connections on port " + listeningSocket.getLocalPort());
            while (true) {
                sockets[index++] = listeningSocket.accept();
                if (index == 3) {
                    break;
                }
            }
            SaatPeSaat sp = new SaatPeSaat(sockets, 3);
            sp.init();
            sp.startGame();
        } catch (IOException e) {
            System.err.println("Server aborted prematurely");
        }
    }

    public static void sendMess(Socket[] sockets) {
        for (int i = 0; i < 3; i++) {
            try {
                DataOutputStream outToClient = new DataOutputStream(sockets[i].getOutputStream());
                String toSend = "String to send\n";
                outToClient.writeBytes(toSend);
            } catch (IOException e) {
                System.out.println("Error\n");
            }
        }
    }
}
