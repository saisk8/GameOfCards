package GameOfCards.Game.SaatPeSaatGame;

import java.io.*;
import java.net.Socket;

public class SaatPeSaatClient {
    Socket player;
    String response;
    int action;

    public SaatPeSaatClient(Socket player) {
        this.player = player;
    }

    public void playGame() {
        System.out.println(1);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        ObjectInputStream inStream = null;
        DataOutputStream outStream = null;
        try {
            System.out.println(3);
            outStream = new DataOutputStream(player.getOutputStream());
            inStream = new ObjectInputStream(player.getInputStream());
        } catch (IOException io) {
            System.out.println("Error...");
        }
        System.out.println(2);
        while (true) {
            System.out.println("Wait for turn...");
            try {
                response = (String) inStream.readObject();
                System.out.println(response);
            } catch (IOException io) {
                io.printStackTrace();
            } catch (ClassNotFoundException cnf) {
                cnf.printStackTrace();
            }
            // System.out.println("Play");
            if (response.endsWith("Select an option: ")) {
                try {
                    action = Integer.parseInt(input.readLine()) - 1;
                    System.out.println(action);
                    outStream.writeInt(action);
                    Thread.sleep(1000);
                } catch (IOException io) {
                    io.printStackTrace();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            } else if (response.endsWith("...")) {
                action = -1;
                try {
                    outStream.writeInt(action);
                } catch (IOException io) {
                    io.printStackTrace();
                }
            } else {
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
