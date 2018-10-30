package GameOfCards;

import java.net.*;
import java.io.*;
import GameOfCards.Basics.Acquaintance;
import GameOfCards.Basics.Comms;
import GameOfCards.Basics.GameServer;

public class ServerStub extends Thread {
    private final static int PORT = 3000;
    static int numberOfGroups = 0;
    static int numberOfPlayersExpected = -1;

    public static void main(String[] args) {
        ServerSocket listeningSocket;
        try {
            listeningSocket = new ServerSocket(PORT);
            System.out.println("Accepting connections on port " + listeningSocket.getLocalPort());
            while (true) {
                Socket newClient = listeningSocket.accept();
                Acquaintance guest =
                        Comms.receiveHello(new ObjectInputStream(newClient.getInputStream()));
                Acquaintance welcome = greetings(guest);
                int game = guest.getOption();
                assignDealer(game, guest);
                Comms.sendWelcome(welcome, new ObjectOutputStream(newClient.getOutputStream()));
            }
        } catch (IOException e) {
            System.err.println("Server aborted prematurely");
        }
    }

    public static Acquaintance greetings(Acquaintance guest) {
        Acquaintance welcome = new Acquaintance();
        if (guest.isHost()) {
            welcome.setStatus(1);
            return welcome;
        }
        welcome.setStatus(-1);
        return welcome;
    }

    public static void assignDealer(int gameId, Acquaintance guest) {
        numberOfGroups++;
        GameServer game = null;
        switch (gameId) {
            case 1:
                game = new GameServer(numberOfGroups, guest.getNumberOfPlayers(), gameId);
                break;
            case 2:
                game = new GameServer(numberOfGroups, guest.getNumberOfPlayers(), gameId);
                break;
            default:
                game = new GameServer(numberOfGroups, guest.getNumberOfPlayers(), 1);
                break;
        }
        game.run();
    }
}
