package crosstheborder.lib;

/**
 * Represents a user of the game
 * @author Joram
 * @version 1.0
 */
public class User {
    private String name;
    private Player player;
    private Lobby lobby;

    /**
     * This is the constructor method of the class "User"
     * in the constructor the name of the player is set
     * @param name the name of the user
     */
    public User(String name){
        this.name = name;
    }

    /**
     * Checks if the user is owner of the lobby
     * @return true if User is owner, false if not or if lobby is null.
     */
    public boolean isOwnerOfLobby(){
        if(lobby.getOwner().equals(this)){
            return true;
        }else {
            return false;
        }
    }
    /**
     * This method sets the value of player
     * @param player the player object the user is playing as
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * This method sets the lobby of the player.
     * @param lobby the lobby object the player is part of
     */
    public void setLobby(Lobby lobby){
        this.lobby = lobby;
    }

    /**
     * This method is used to get the player object of the user
     * @return Player the player object of the user
     */
    public Player getPlayer(){return player;}

    /**
     * This method is used to get the name of the user
     * @return String the name of the user
     */
    public String getName(){
        return name;
    }

    /**
     * This method gets the Lobby object this player is part of
     * @return Lobby the lobby the user is in null if not in a lobby.
     */
    public Lobby getLobby() {
        return lobby;
    }
}