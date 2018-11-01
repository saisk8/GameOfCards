package GameOfCards.Game.SaatPeSaatGame;

import java.io.*;
import java.net.Socket;

/**
 * A class that implements the Saat Pe Saat client
 */
public class SaatPeSaatClient {
    Socket player;
    String response;
    int action;

    /**
     * The constructor of the class
     * 
     * @param player The socket object that is connected to the dedicated server
     */
    public SaatPeSaatClient(Socket player) {
        this.player = player;
    }

    /**
     * The method that allows the user to play the game
     */
    public void playGame() {
        // System.out.println(1);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        ObjectInputStream inStream = null;
        ObjectOutputStream outStream = null;
        try {
            // System.out.println(3);
            outStream = new ObjectOutputStream(player.getOutputStream());
            inStream = new ObjectInputStream(player.getInputStream());
        } catch (IOException io) {
            System.out.println("Error...");
        }
        // System.out.println(2);
        while (true) {
            System.out.println("Please wait for turn...\n\n");
            try {
                response = (String) inStream.readObject();
                System.out.println(response);
            } catch (IOException io) {
                io.printStackTrace();
            } catch (ClassNotFoundException cnf) {
                cnf.printStackTrace();
            }
            // System.out.println("Play");
            if (response.endsWith("(from 1 to n): ")) {
                try {
                    action = Integer.parseInt(input.readLine()) - 1;
                    // System.out.println(action);
                    outStream.writeObject(action);
                    Thread.sleep(1000);
                } catch (IOException io) {
                    io.printStackTrace();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            } else if (response.endsWith("...")) {
                action = -1;
                try {
                    outStream.writeObject(action);
                } catch (IOException io) {
                    io.printStackTrace();
                }
            } else {
                try {
                    player.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
                break;
            }
        }
        try {
            input.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
