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
    GameServer game;

    public ServerStub(GameServer game) {
        this.game = game;

    }

    public static void main(String[] args) {
        ServerSocket listeningSocket;
        try {
            listeningSocket = new ServerSocket(PORT);
            System.out.println("Accepting connections on port " + listeningSocket.getLocalPort());
            while (true) {
                // System.out.println("Again");
                Socket newClient = listeningSocket.accept();
                Acquaintance guest = Comms.receiveHello(new ObjectInputStream(newClient.getInputStream()));
                Acquaintance welcome = greetings(guest);
                int game = guest.getOption();
                ServerStub stub = assignDealer(game, guest);
                stub.start();
                Comms.sendWelcome(welcome, new ObjectOutputStream(newClient.getOutputStream()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Server aborted prematurely " + PORT);
        }
    }

    public static Acquaintance greetings(Acquaintance guest) {
        numberOfGroups++;
        Acquaintance welcome = new Acquaintance();
        if (guest.isHost()) {
            welcome.setStatus(1);
            welcome.setGroupId(numberOfGroups);
            welcome.setOption(guest.getOption());
            return welcome;
        }
        welcome.setStatus(-1);
        return welcome;
    }

    public void run() {
        game.start();
    }

    public static ServerStub assignDealer(int gameId, Acquaintance guest) {
        GameServer newGame = null;
        System.out.println(numberOfGroups);
        switch (gameId) {
        case 1:
            newGame = new GameServer(numberOfGroups, guest.getNumberOfPlayers(), gameId);
            break;
        case 2:
            newGame = new GameServer(numberOfGroups, guest.getNumberOfPlayers(), gameId);
            break;
        default:
            newGame = new GameServer(numberOfGroups, guest.getNumberOfPlayers(), 1);
            break;
        }
        return new ServerStub(newGame);
    }
}
