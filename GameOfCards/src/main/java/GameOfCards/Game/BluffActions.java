package GameOfCards.Game;

import java.io.*;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Implementation of the rules for the dumb card game.
 */
public class BluffActions implements Serializable {
    private static final long serialVersionUID = 2;
    private int option = -1;
    private int[] indices;

    public void setOption() {
        System.out.println("Menu:");
        System.out.println("1. Call Bluff\n2. Pass\n 3. Play");
        Scanner input = new Scanner(System.in);
        System.out.print("Enter an option: ");
        option = input.nextInt();
        input.close();
        System.out.println("You entered: " + option);
        setAdditionalData();
    }

    public void setAdditionalData() {
        if (option == 3) {
            System.out.print("Enter the indices of cards to be played: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String lines = " ";
            try {
                lines = br.readLine();
            } catch (IOException io) {
                System.out.println("Error");
            }
            String[] strs = lines.trim().split("\\s+");
            indices = new int[strs.length];
            for (int i = 0; i < strs.length; i++) {
                indices[i] = Integer.parseInt(strs[i]);
            }
        } else if (option == -1) {
            System.out.println("Error: Invalid option");
        }
        return;
    }

    public int getOption() {
        return option;
    }

    public int[] getIndices() {
        return indices;
    }
}
