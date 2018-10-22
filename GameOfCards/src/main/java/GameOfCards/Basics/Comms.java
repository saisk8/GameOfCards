package GameOfCards.Basics;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Comms implements Serializable {
    private final static long serialVersionUID = 1;

    public static void sendData(Socket soc, Object data) {
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(soc.getOutputStream());
            outStream.writeObject(data);
        } catch (IOException io) {
            System.out.println("Comms Send error: IO");
        }
    }

    public static Object receiveData(Socket soc) {
        Object data = null;
        try {
            ObjectInputStream inStream = new ObjectInputStream(soc.getInputStream());
            data = inStream.readObject();
        } catch (IOException io) {
            System.out.println("Comms Receive error: IO");
        } catch (ClassNotFoundException cnf) {
            System.out.println("Comms Receive error: Class not found");
        }
        if (data != null) {
            return data;
        }
        return null;
    }

    public static void alertPlayerTurn(Socket soc) {
        // Player's turn
        int status = 1;
        try {
            DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
            dos.writeInt(status);
        } catch (IOException io) {
            System.out.println("Comms alertPlayerTurn error: io");
        }
    }

    public static boolean receiveTurnAlert(Socket soc) {
        int status = 1;
        try {
            DataInputStream dis = new DataInputStream(soc.getInputStream());
            status = dis.readInt();
        } catch (Exception e) {
            System.out.println("Comms ReceiveTurnAlert error");
        }
        if (status == 0 || status == -1)
            return false;
        return true;
    }

    public static void declareWinner(Socket[] players, int winner) {
        int status = -1;
        for (int i = 0; i < players.length; i++) {
            int v = status;
            if (i == winner)
                v = 2;
            try {
                DataOutputStream dos = new DataOutputStream(players[i].getOutputStream());
                dos.writeInt(v);
            } catch (IOException io) {
                System.out.println("Comms declareWinner error: io");
            }
        }
    }
}
