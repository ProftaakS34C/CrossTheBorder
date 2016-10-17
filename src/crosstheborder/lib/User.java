package crosstheborder.lib;

/**
 * Represents a user of the game.
 *
 * @author Joram
 * @author Oscar de Leeuw
 * @version 1.0
 */
public class User {
    private String name;
    private Player player;
    private Lobby lobby;
    private boolean isComputer;

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
        return lobby.getOwner().equals(this);
    }

    /**
     * Method used for filling the owner column.
     *
     * @return A string that represents whether this user is the owner of the lobby.
     */
    public String getOwner() {
        return isOwnerOfLobby() ? "Yes" : "No";
    }

    /**
     * Gets whether this user should be treated as a computer.
     *
     * @return True when this user is a computer.
     */
    public boolean isComputer() {
        return this.isComputer;
    }

    /**
     * Turn this user into a computer.
     */
    public void turnIntoComputer() {
        this.isComputer = true;
    }

    /**
     * This method sets the lobby of the player.
     * @param lobby the lobby object the player is part of
     */
    public void joinLobby(Lobby lobby) {
        this.lobby = lobby;
        lobby.addUser(this);
    }

    public void leaveLobby() {
        if (this.lobby != null) {
            this.lobby.removeUser(this);
            this.lobby = null;
        }
    }

    /**
     * This method is used to get the player object of the user
     * @return Player the player object of the user
     */
    public Player getPlayer(){return player;}

    /**
     * This method sets the value of player
     *
     * @param player the player object the user is playing as
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

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