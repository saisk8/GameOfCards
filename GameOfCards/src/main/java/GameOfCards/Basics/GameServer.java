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
                SaatPeSaatServer game1 = new SaatPeSaatServer(portOffset, numberOfPlayers);
                game1.init();

            case 2:
                DumbGameServer game2 = new DumbGameServer(portOffset, numberOfPlayers);
                game2.init();
            default:
                System.err.println("Some error occured");
        }
    }
}
