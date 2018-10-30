package GameOfCards.Basics;

import GameOfCards.Game.SaatPeSaatGame.SaatPeSaatServer;
import GameOfCards.Game.DumbGame.DumbGameServer;;

public class GameServer implements Runnable {
    private int portOffset;
    private int numberOfPlayers;
    private int gameId;

    public GameServer(int portOffset, int numberOfPlayers, int gameId) {
        this.portOffset = portOffset;
        this.numberOfPlayers = numberOfPlayers;
        this.gameId = gameId;
    }

    public void run() {
        switch (gameId) {
            case 1:
                System.out
                        .println("Game ID: " + gameId + ", NUmber Of Players: " + numberOfPlayers);
                SaatPeSaatServer game1 = new SaatPeSaatServer(portOffset, numberOfPlayers);
                game1.init();
                break;

            case 2:
                System.out
                        .println("Game ID: " + gameId + ", NUmber Of Players: " + numberOfPlayers);
                DumbGameServer game2 = new DumbGameServer(portOffset, numberOfPlayers);
                game2.init();
                break;
            default:
                System.err.println("Some error occured");
        }
    }
}
