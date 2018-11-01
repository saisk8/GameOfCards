package GameOfCards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import GameOfCards.Basics.Acquaintance;
import GameOfCards.Basics.Comms;
import GameOfCards.Game.DumbGame.DumbClient;
import GameOfCards.Game.SaatPeSaatGame.*;

/**
 * The class that implements all the client dealings with the application
 */
public class ClientStub {
    public static void main(String argv[]) throws Exception {
        int host = isHost();
        Acquaintance client = doNeedFull(host);
        Socket soc = null;
        Socket player = null;
        Acquaintance reply = null;
        if (host == 1) {
            try {
                soc = new Socket("localhost", 3000);
                Comms.sendWelcome(client, new ObjectOutputStream(soc.getOutputStream()));
                reply = Comms.receiveHello(new ObjectInputStream(soc.getInputStream()));
                // System.out.println(reply.getGroupId());
                try {
                    // System.out.println(11);
                    int port = 3000 + reply.getGroupId();
                    System.out.println("Port: " + port);
                    player = new Socket("localhost", port);
                    // System.out.println(22);
                    System.out.println("Connected to new Server at " + player.getPort());
                    System.out.println("Your group ID = " + (player.getPort() - 3000));
                    System.out.println("Wait until other players join... Thank you!");
                } catch (IOException io) {
                    io.printStackTrace();
                }
            } catch (IOException io) {
                io.printStackTrace();
                soc.close();
                return;
            }
            soc.close();
        } else {
            System.out.println("Enter your group ID: ");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
                int port = 3000 + Integer.parseInt(input.readLine());
                System.out.println("Port: " + port);
                player = new Socket("localhost", port);
                System.out.println("Connected to new Server at" + player.getPort());
                System.out.println("Wait until other players join... Thank you!");
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        switch (client.getOption()) {
            case 1:
                SaatPeSaatClient game1 = new SaatPeSaatClient(player);
                game1.playGame();
                break;
            case 2:
                DumbClient game2 = new DumbClient(player);
                game2.playGame();
                break;
            default:
                System.err.println("Some error occured " + client.getOption());
        }
    }

    /**
     * A function to check if current user wants to be a host
     * 
     * @return A boolean value confirming the host status
     */
    public static int isHost() {
        int option = -1;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Are you a Host(1) or not(2): ");
        do {
            try {
                option = Integer.parseInt(input.readLine());
            } catch (IOException io) {
                io.printStackTrace();
            }
        } while (option != 1 && option != 2);
        return option;
    }

    /**
     * A class that fills in the general information required by the applicatioon to start or find
     * the game
     * 
     * @param isHost If the player is the host; A boolean value
     * @return An Acquaintance object with all the required information that is sent to the server
     */
    public static Acquaintance doNeedFull(int isHost) {
        Acquaintance client = new Acquaintance();
        if (isHost == 1) {
            System.out.println("Which game do you want to host? ");
            System.out.println("1. Saat Pe Saat\n2. Dumb Card Game");
            System.out.print("Enter your option: ");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
                int a = Integer.parseInt(input.readLine());
                client.setOption(a);
                System.out.println("Enter the number of players: ");
                int b = Integer.parseInt(input.readLine());
                // System.out.println(b);
                client.setNumberOfPlayers(b);
            } catch (IOException io) {
                io.printStackTrace();
            }
            client.setHost();
        } else {
            System.out.println("Which game do you want to host? ");
            System.out.println("1. Saat Pe Saat\n2. Dumb Card Game");
            System.out.print("Enter your option: ");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
                int a = Integer.parseInt(input.readLine());
                client.setOption(a);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        return client;
    }
}
