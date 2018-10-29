package GameOfCards.Game.DumbGame;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class DumbClient {
    Socket player;
    String response;

    public DumbClient(Socket player) {
        this.player = player;
    }

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

