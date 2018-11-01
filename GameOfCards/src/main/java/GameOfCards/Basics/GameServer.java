package GameOfCards.Basics;

import GameOfCards.Game.SaatPeSaatGame.SaatPeSaatServer;
import GameOfCards.Game.DumbGame.DumbGameServer;;

/**
 * A class that extends the Thread class and implement the basic thread class of the applicatiuon to
 * create new dedicated servers
 */
public class GameServer extends Thread {
    private int portOffset;
    private int numberOfPlayers;
    private int gameId;

    /**
     * To create a new GameServer object
     * 
     * @param portOffset      The group ID decided
     * @param numberOfPlayers The number of players in the game
     * @param gameId          The game ID (Saat pe Saat or Dumb game)
     */
    public GameServer(int portOffset, int numberOfPlayers, int gameId) {
        this.portOffset = portOffset;
        this.numberOfPlayers = numberOfPlayers;
        this.gameId = gameId;
    }

    /**
     * The run method of the Server to create a new dedicated server
     */
    public void run() {
        switch (gameId) {
            case 1:
                System.out
                        .println("Game ID: " + gameId + ", Number Of Players: " + numberOfPlayers);
                SaatPeSaatServer game1 = new SaatPeSaatServer(portOffset, numberOfPlayers);
                game1.init();
                break;

            case 2:
                System.out
                        .println("Game ID: " + gameId + ", Number Of Players: " + numberOfPlayers);
                DumbGameServer game2 = new DumbGameServer(portOffset, numberOfPlayers);
                game2.init();
                break;
            default:
                System.err.println("Some error occured");
        }
    }
}
