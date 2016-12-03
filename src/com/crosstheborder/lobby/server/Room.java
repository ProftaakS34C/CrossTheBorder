package com.crosstheborder.lobby.server;

import crosstheborder.lib.Game;
import crosstheborder.lib.Message;
import crosstheborder.lib.User;
import crosstheborder.lib.interfaces.GameSettings;
import com.crosstheborder.lobby.shared.IRoom;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a room in the lobby
 * @author Joram
 * @author Oscar de Leeuw
 * @author Yannic
 * @version 1.0
 */
public class Room extends UnicastRemoteObject implements IRoom, Serializable{
    private final List<String> NAME_POOL = Arrays.asList("Henk", "Piet", "Arnold", "Gert", "Adolf", "Phuc", "Nico", "Gert-Jan",
            "xXSniperEliteXx", "SuperMaster69", "EzGame4613", "Ferdinand", "MexicanDestroyer", "ЦукаБлзат", "ИдиНахуи", "PenPineappleApplePen",
            "MaatwerkBoi", "ManManMan", "PannenkoekenKoekenpan", "MaatwerkExceptie", "GrijsGebied", "MeneerPopo");
    private final Iterator<String> AI_NAMES;

    private String name;
    private String password;
    private int maxPlayers;
    private ArrayList<Message> messages;
    private ArrayList<User> users;
    private User owner;
    private Game game;
    private GameSettings settings;


    /**
     * This is the constructor method of the class "Room"
     * @param name The name of the lobby
     * @param maxPlayers The maximum amount of players allowed in the lobby
     */
    public Room(User owner, String name, int maxPlayers) throws RemoteException{
        this(owner, name, "", maxPlayers);
    }
    /**
     * This is the constructor method of the class "Room"
     * @param name The name of the lobby
     * @param password The password of the lobby
     * @param maxPlayers The maximum amount of players allowed in the lobby
     */
    public Room(User owner, String name, String password, int maxPlayers) throws RemoteException{
        this.owner = owner;
        this.name = name;
        this.password = password;
        this.maxPlayers = maxPlayers;
        this.messages = new ArrayList<>();
        this.users = new ArrayList<>();
        this.owner.joinRoom(this);

        Collections.shuffle(NAME_POOL);
        AI_NAMES = NAME_POOL.iterator();
    }

    /**
     * This method is used to get the name of the lobby object
     * @return String this returns the name of the lobby object
     */
    @Override
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
     * Gets the gameSettings of this lobby
     * @return a GameSettings object containing all settings
     */
    public GameSettings getSettings() {
        return settings;
    }


    public boolean getIsPrivate() {
        return password.equals("") ? false : true;
    }

    /**
     * This method gets the game of this lobby
     * @return Game, the game object of this lobby
     */
    public Game getGame(){
        return game;
    }

    /**
     * Gets the users that are present in this lobby.
     *
     * @return The list of users.
     */
    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
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
     * Adds a user to the array list of users in the lobby
     *
     * @param user The user to add
     * @return a boolean value indicating success
     */
    @Override
    public boolean addUser(User user) throws RemoteException {
        if (maxPlayers >= users.size() && !users.contains(user)) {
            users.add(user);

            if (owner == null) {
                owner = user;
            }
            return true;
        }
        return false;
    }

    /**
     * Removes a user from the array list of users in the lobby
     * only removes if the user is present in list
     *
     * @param user The user to remove
     * @return True when the lobby should stay alive. False when the lobby should be removed.
     */
    @Override
    public boolean removeUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
            List<User> humans = getAllHumanUsers();

            //If we just removed the owner assign it to someone else.
            if (humans.isEmpty()) {
                //TODO DESTROY LOBBY
                if (game != null) {
                    game.stop();
                }
                return false;
            } else if (owner == user) {
                owner = humans.get(0);
            }
        }
        return true;
    }

    private List<User> getAllHumanUsers() {
        return users.stream().filter(u -> !u.isComputer()).collect(Collectors.toList());
    }

    public void addAI() {
        User computer = new User(AI_NAMES.next());
        computer.turnIntoComputer();
        computer.joinRoom(this);
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
    @Override
    public boolean addMessage(Message message) throws RemoteException{
        return messages.add(message);
    }

    /**
     * This method is used to get all messages from this lobby.
     * @return List of messages
     */
    public ArrayList<Message> getMessages() {
        return this.messages;
    }

    /**
     * This method is used to start the game
     */
    @Override
    public void startGame(String mapName) throws RemoteException{
        Game game = new Game(mapName);
        //game.getSettings(settings);
        ArrayList<User> randomList = new ArrayList<>(users);
        Collections.shuffle(randomList);

        game.addPlayer(new User("Trump")); //TODO TESTING CODE.

        for (User u : randomList) {
            game.addPlayer(u);
        }

        game.startGame();
        this.game = game;
    }
}