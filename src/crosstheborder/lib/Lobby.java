package crosstheborder.lib;

import java.util.ArrayList;

/**
 * Represents a lobby of the game
 * @author Joram
 * @version 1.0
 */
public class Lobby {
    private String name;
    private String password;
    private int maxPlayers;
    private ArrayList<Message> messages;
    private ArrayList<User> users;
    private User owner;
    private Game game;

    /**
     * This is the constructor method of the class "Lobby"
     * @param name The name of the lobby
     * @param maxPlayers The maximum amount of players allowed in the lobby
     */
    public Lobby(User owner, String name, int maxPlayers){
        this(owner, name, "", maxPlayers);
    }
    /**
     * This is the constructor method of the class "Lobby"
     * @param name The name of the lobby
     * @param password The password of the lobby
     * @param maxPlayers The maximum amount of players allowed in the lobby
     */
    public Lobby(User owner, String name, String password, int maxPlayers){
        this.owner = owner;
        this.name = name;
        this.password = password;
        this.maxPlayers = maxPlayers;
        this.messages = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    /**
     * This method is used to get the name of the lobby object
     * @return String this returns the name of the lobby object
     */
    public String getName(){
        return name;
    }

    /**
     * This method is used to set the name of the lobby
     * @param value the new name of the lobby
     */
    public void setName(String value){
        name = value;
    }

    /**
     * This method is used to get the password of the lobby object
     * @return String this returns the password of the lobby object
     */
    public String getPassword() {
        return name;
    }

    /**
     * This method is used to set the password of the lobby
     * @param value the new password of the lobby
     */
    public void setPassword(String value){
        password = value;
    }

    /**
     * This method gets the game of this lobby
     * @return Game, the game object of this lobby
     */
    public Game getGame(){
        return game;
    }

    /**
     * This method is used to set the game of the lobby
     * @param game the new game for the lobby
     */
    public void setGame(Game game){
        this.game = game;
    }

    /**
     * This method is used to get the maximum amount of players allowed inside the lobby
     *
     * @return int  this returns the maximum amount of players allowed inside the lobby
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * This method is used to set the maximum amount of players allowed inside the lobby
     * @param value the new maximum amount of players allowed inside the lobby
     */
    public void setMaxPlayers(int value){
        maxPlayers = value;
    }

    /**
     * This method returns the owner of this lobby
     * @return the User object which is owner of the lobby.
     */
    public User getOwner() {
        return owner;
    }
    /**
     * This method is used when a user in the lobby sends a message
     * @param message the message object that you want to add to the lobby
     * @return boolean true if message is added
     */
    public boolean addMessage(Message message){
        return messages.add(message);
    }

    /**
     * This method is used to get all messages from this lobby.
     * @return List of messages
     */
    public ArrayList<Message> getMessages(){ return this.messages; }

    /**
     * This method is used to start the game
     */
    public void startGame(String mapName) {
        Game game = new Game(mapName);
        for(User u : users){
            game.addPlayer(u);
        }
    }
}