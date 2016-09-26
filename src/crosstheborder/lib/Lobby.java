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

    /**
     * This is the constructor method of the class "Lobby"
     * @param name The name of the lobby
     * @param password The password of the lobby
     * @param maxPlayers The maximum amount of players allowed in the lobby
     */
    public Lobby(String name, String password, int maxPlayers){
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
     * This method is used to get the password of the lobby object
     * @return String this returns the password of the lobby object
     */
    public String getPassword(){
        return name;
    }

    /**
     * This method is used to get the maximum amount of players allowed inside the lobby
     * @return int  this returns the maximum amount of players allowed inside the lobby
     */
    public int getMaxPlayers(){
        return maxPlayers;
    }

    /**
     * This method is used to set the name of the lobby
     * @param value the new name of the lobby
     */
    public void setName(String value){
        name = value;
    }

    /**
     * This method is used to set the password of the lobby
     * @param value the new password of the lobby
     */
    public void setPassword(String value){
        password = value;
    }

    /**
     * This method is used to set the maximum amount of players allowed inside the lobby
     * @param value the new maximum amount of players allowed inside the lobby
     */
    public void setMaxPlayers(int value){
        maxPlayers = value;
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
     * This method is used to start the game
     */
    public void startGame(){

    }
}