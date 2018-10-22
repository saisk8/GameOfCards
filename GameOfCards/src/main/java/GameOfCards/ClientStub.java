package GameOfCards;

import java.net.*;
import GameOfCards.Game.SaatPeSaatGame.*;

public class ClientStub {
    public static void main(String argv[]) throws Exception {
        Socket clientSocket = new Socket("localhost", 3000);
        SaatPeSaatClient c = new SaatPeSaatClient(clientSocket);
        c.playGame();
        clientSocket.close();
    }
}
