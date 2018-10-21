package GameOfCards.Game.SaatPeSaatGame;

import GameOfCards.Basics.*;

public class SaatPeSaat {
    private Deck cardDeck, discardDeck;
    private BluffHand[] playerHand;
    private final int INITIAL_SIZE_OF_HAND = 8;
    private final int NUMBER_OF_PLAYERS;
    private Socket[] players;
    private boolean bluffed = false;
    private String lastAction = "";
}
