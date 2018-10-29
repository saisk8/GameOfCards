package GameOfCards.Basics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Comms implements Serializable {
    private final static long serialVersionUID = 1;


    public static void sendWelcome(Object data, ObjectOutputStream out) {
        try {
            out.writeObject(data);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static Acquaintance receiveHello(ObjectInputStream in) {
        try {
            return (Acquaintance) in.readObject();
        } catch (IOException io) {
            io.printStackTrace();
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        return null;
    }

    /**
     * Sends an data of type <code> Object </code> through the output stream
     */
    public static void sendData(ObjectOutputStream outStream, Object data) {
        try {
            outStream.writeObject(data);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * Receives data of type <code> int </code> from the DataInputStream.
     * 
     * @param inStream A ObjectInputStream that is wrapped around a TCP socket's InputStream
     * @return An Object value read from the InputStream
     */
    public static Object receiveData(ObjectInputStream inStream) {
        Object data = null;
        try {
            data = inStream.readObject();
        } catch (IOException io) {
            io.printStackTrace();
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        return data;
    }

    /**
     * Sends to each of the connected clients their winning status respectively.
     * 
     * @param outds             An array of ObjectOutputStream
     * @param turn              The index of the player whose turn it is currently
     * @param checkLoop         A boolean value to check if all players have lost
     * @param NUMBER_OF_PLAYERS Number of players, playing the game
     */
    public static void declareWinner(ObjectOutputStream[] outds, int turn, boolean checkLoop,
            int NUMBER_OF_PLAYERS) {
        int index = turn;
        if (checkLoop) {
            do {
                try {
                    outds[index].writeObject("You Lose!\n\n");
                    outds[index].close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
                index = (index + 1) % NUMBER_OF_PLAYERS;
            } while (index != turn);
            return;
        }
        try {
            outds[index].writeObject("You Win!\n\n");
            outds[index].close();
        } catch (IOException io) {
            io.printStackTrace();
        }
        do {
            try {
                outds[index].writeObject("You Lose!\n\n");
                outds[index].close();
            } catch (IOException io) {
                io.printStackTrace();
            }
            index = (index + 1) % NUMBER_OF_PLAYERS;
        } while (index != turn);
    }
}
