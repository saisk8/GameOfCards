import java.net.*;
import java.io.*;
import java.util.*;

public class Server extends Thread {

    Socket theConnection;

    public Server(Socket s) {
        theConnection = s;
    }

    public static void main(String[] args) {

        int thePort;
        ServerSocket listeningSocket;

        // set the port to listen on
        try {
            thePort = Integer.parseInt(args[0]);
            if (thePort < 0 || thePort > 65535)
                thePort = 3000;
        } catch (Exception e) {
            thePort = 3000;
        }

        try {
            int index = 0;
            listeningSocket = new ServerSocket(thePort);
            System.out.println("Accepting connections on port " + listeningSocket.getLocalPort());
            while (true) {
                Socket sockets[] = new Socket[10];
                sockets[index++] = listeningSocket.accept();
                if (index == 4) {
                    sendMess(sockets);
                    index++;
                }
            }
        } catch (IOException e) {
            System.err.println("Server aborted prematurely");
        }
    }

    public static void sendMess(Socket[] sockets) {
        for (int i = 0; i < sockets.length; i++) {
            try {
                OutputStream outstream = sockets[i].getOutputStream();
                PrintWriter out = new PrintWriter(outstream);
                String toSend = "String to send";
                out.print(toSend);

            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
}
