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

public class ClientStub {
    public static void main(String argv[]) throws Exception {
        int groupId = isHost();
        Acquaintance client = doNeedFull(groupId);
        Socket player = connect(client);
        switch (client.getOption()) {
            case 1:
                SaatPeSaatClient game1 = new SaatPeSaatClient(player);
                game1.playGame();
            case 2:
                DumbClient game2 = new DumbClient(player);
                game2.playGame();
            default:
                System.err.println("Some error occured");
        }
    }

    public static int isHost() {
        int option = -1;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Are you a Host(1) or not(2): ");
        try {
            option = Integer.parseInt(input.readLine());
        } catch (IOException io) {
            io.printStackTrace();
        }
        return option;
    }

    public static Acquaintance doNeedFull(int isHost) {
        Acquaintance client = new Acquaintance();
        if (isHost == 1) {
            System.out.println("Which game do you want to host? ");
            System.out.println("1. Saat Pe Saat\n2. Dumb Card Game");
            System.out.print("Enter your option: ");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
                client.setOption(Integer.parseInt(input.readLine()));
            } catch (IOException io) {
                io.printStackTrace();
            }
            client.setHost();
        } else {
            return client;
        }
        return client;
    }

    public static Socket connect(Acquaintance client) {
        Acquaintance reply;
        Socket game = null;
        if (client.isHost()) {
            try {
                Socket soc = new Socket("localhost", 3000);
                Comms.sendWelcome(client, new ObjectOutputStream(soc.getOutputStream()));
                reply = Comms.receiveHello(new ObjectInputStream(soc.getInputStream()));
                game = new Socket("localhost", 3000 + reply.getGroupId());
                soc.close();
            } catch (IOException io) {
                io.printStackTrace();
            }
            return game;
        }
        System.out.print("Enter your group ID: ");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            game = new Socket("localhost", 3000 + Integer.parseInt(input.readLine()));
        } catch (IOException io) {
            io.printStackTrace();
        }
        return game;
    }
}
