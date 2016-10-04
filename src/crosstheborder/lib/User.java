package crosstheborder.lib;

/**
 * Represents a user of the game
 * @author Joram
 * @version 1.0
 */
public class User {
    private String name;
    private Player player;

    /**
     * This is the constructor method of the class "User"
     * in the constructor the name of the player is set
     * @param name the name of the user
     */
    public User(String name){
        this.name = name;
    }

    /**
     * This method sets the value of player
     * @param player the player object the user is playing as
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * This method is used to get the name of the user
     * @return String the name of the user
     */
    public String getName(){
        return name;
    }
}