package GameOfCards.Game.BluffGame;

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
    private String[] ranks =
            {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    private String action = "";

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
            System.out.println(ranks.toString());
            System.out.print("Enter the Rank index: ");
            Scanner input = new Scanner(System.in);
            int index = input.nextInt();
            index -= 1;
            System.out.println("Your selected rank: " + ranks[index]);
            input.close();
            action += Integer.toString(indices.length) + " " + ranks[index];
        } else if (option == -1) {
            System.out.println("Error: Invalid option");
        }
        option = -1;
        return;
    }

    public int getOption() {
        return option;
    }

    public int[] getIndices() {
        return indices;
    }

    public String lastAction() {
        return action;
    }
}
