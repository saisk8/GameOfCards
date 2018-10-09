import java.net.*;
import java.io.*;
import java.util.*;

public class Server extends Thread {

    Socket theConnection;

    public jhttp(Socket s) {
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
            listeningSocket = new ServerSocket(thePort);
            System.out.println("Accepting connections on port " + listeningSocket.getLocalPort());
            while (true) {
                // A new thread is started for each request
                Server s = new Server(listeningSocket.accept());
                s.start();
            }
        } catch (IOException e) {
            System.err.println("Server aborted prematurely");
        }
    }

    public void run() {
    }
}