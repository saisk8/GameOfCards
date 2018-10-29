package GameOfCards.Basics;

import java.io.Serializable;

public class Acquaintance implements Serializable {
    private final static long serialVersionUID = 1;
    private int option;
    private int groupId;
    private int NUMBER_OF_PLAYERS;
    private boolean isHost = false;
    private int status = 0;

    public void setOption(int option) {
        this.option = option;
    }

    public int getOption() {
        return option;
    }

    public void setGroupId(int id) {
        groupId = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void displayGames() {
        System.out.println("The available games are: ");
        System.out.println("1. Saat Pe Saat");
        System.out.println("2. Dumb Card Game");
        System.out.println("Select your game");
    }

    public void setHost() {
        isHost = true;
    }

    public boolean isHost() {
        return isHost;
    }


    public void setNumberOfPlayers(int players) {
        NUMBER_OF_PLAYERS = players;
    }

    public int getNumberOfPlayers() {
        return NUMBER_OF_PLAYERS;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
