package GameOfCards.Game.DumbGame;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * The class that implements a Dumb games's client
 */
public class DumbClient {
    Socket player;
    String response;

    /**
     * To create a new Dumb game client object
     * 
     * @param player The Socket object of the client that is connected to the dedicated server that
     *               plays the dumb game
     */
    public DumbClient(Socket player) {
        this.player = player;
    }

    /**
     * Function that allows the client to play the game
     */
    public void playGame() {
        ObjectInputStream inStream = null;
        try {
            inStream = new ObjectInputStream(player.getInputStream());
        } catch (IOException io) {
            io.printStackTrace();
        }
        try {
            response = (String) inStream.readObject();
            System.out.println(response);
        } catch (IOException io) {
            io.printStackTrace();
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        try {
            inStream.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

