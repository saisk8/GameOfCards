package GameOfCards.Game;

import java.io.*;
import java.io.Serializable;
import GameOfCards.Basics.*;

/**
 * Implementation of the rules for the dumb card game.
 */
public class BluffHand extends Hand implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * Creates a hand for the dumb card game.
     */
    public BluffHand() {
        super();
    }
}
