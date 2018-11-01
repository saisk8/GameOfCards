package GameOfCards.Basics;

import java.io.Serializable;
/**
 * A class that implements the object that is used in initial stages of hosting a new game 
 */
public class Acquaintance implements Serializable {
    private final static long serialVersionUID = 1;
    private int option;
    private int groupId;
    private int NUMBER_OF_PLAYERS;
    private boolean isHost = false;
    private int status = 0;
    /**
    * To Set the Option form the user
    */
    public void setOption(int option) {
        this.option = option;
    }
    /**
    * To get the value of option
    */
    public int getOption() {
        return option;
    }
    /**
    * To set the group ID
    */
    public void setGroupId(int id) {
        groupId = id;
    }
    /**
    * To get the groupID of the user
    */
    public int getGroupId() {
        return groupId;
    }
    /**
    * To display the games available
    */
    public void displayGames() {
        System.out.println("The available games are: ");
        System.out.println("1. Saat Pe Saat");
        System.out.println("2. Dumb Card Game");
        System.out.println("Select your game");
    }
    /**
    * To set the host status of the client
    */
    public void setHost() {
        isHost = true;
    }
    /**
    * To get the host status of the client
    */
    public boolean isHost() {
        return isHost;
    }

    /**
    * To set number of players
    */
    public void setNumberOfPlayers(int players) {
        NUMBER_OF_PLAYERS = players;
    }
    /**
    * To get the number of players
    */
    public int getNumberOfPlayers() {
        return NUMBER_OF_PLAYERS;
    }
    /**
    * To set the error status
    */
    public void setStatus(int status) {
        this.status = status;
    }
    /**
    * To get the error status
    */
    public int getStatus() {
        return status;
    }
}
