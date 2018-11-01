package GameOfCards;

import java.net.*;
import java.io.*;
import GameOfCards.Basics.Acquaintance;
import GameOfCards.Basics.Comms;
import GameOfCards.Basics.GameServer;

/**
 * A class that impelemnts the main server that runs at port 3000
 */
public class ServerStub extends Thread {
    private final static int PORT = 3000;
    static int numberOfGroups = 0;
    static int numberOfPlayersExpected = -1;
    GameServer game;

    /**
     * A construtor of the Server
     * 
     * @param game The object is of type GameServer and has information on the dedicated server
     *             required
     */
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
                Acquaintance guest =
                        Comms.receiveHello(new ObjectInputStream(newClient.getInputStream()));
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

    /**
     * A method to get the required information for creating the reply object
     * 
     * @param guest An object of Type Acquaintance that is received from the client
     * @return A new Acquaintance object that which is sent back to the client
     */
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

    /**
     * The implementation of method inherited from the Thread class
     */
    public void run() {
        game.start();
    }

    /**
     * The method that creates a new dedicated server
     * 
     * @param gameId The iD of the game (Saat pe Saat or Dumn game)
     * @param guest  The Acquaintance object with the required information to create the dedicated
     *               server
     * @return A ServerStub object that creates a new dedicatded server in a thread
     */
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
