package com.crosstheborder.lobby.server;

import com.crosstheborder.game.server.GameServer;
import com.crosstheborder.game.shared.network.RMIConstants;
import crosstheborder.lib.Game;
import crosstheborder.lib.Message;
import crosstheborder.lib.User;
import crosstheborder.lib.interfaces.GameSettings;
import com.crosstheborder.lobby.shared.IRoom;

import java.io.*;
import java.net.Socket;
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

    private String bindingName;
    private boolean gameStarted;

    /**
     * This is the constructor method of the class "Room"
     * @param name The name of the room
     * @param maxPlayers The maximum amount of players allowed in the room
     */
    public Room(User owner, String name, int maxPlayers) throws RemoteException{
        this(owner, name, "", maxPlayers);
    }
    /**
     * This is the constructor method of the class "Room"
     * @param owner the creator of the room
     * @param name The name of the room
     * @param password The password of the room
     * @param maxPlayers The maximum amount of players allowed in the room
     */
    public Room(User owner, String name, String password, int maxPlayers) throws RemoteException{
        this.owner = owner;
        this.name = name;
        this.password = password;
        this.maxPlayers = maxPlayers;
        this.messages = new ArrayList<>();
        this.users = new ArrayList<>();
        this.owner.setRoom(this);

        addUser(owner);

        Collections.shuffle(NAME_POOL);
        AI_NAMES = NAME_POOL.iterator();
    }

    /**
     * This method is used to get the name of the room object
     * @return String this returns the name of the room object
     */
    @Override
    public String getName(){
        return name;
    }

    /**
     * This method is used to set the name of the room
     * @param value the new name of the room
     */
    public void setName(String value){
        name = value;
    }

    /**
     * This method is used to get the password of the room object
     * @return String this returns the password of the room object
     */
    public String getPassword() {
        return name;
    }

    /**
     * This method is used to set the password of the room
     * @param value the new password of the room
     */
    public void setPassword(String value){
        password = value;
    }

    /**
     * Gets the gameSettings of this room
     * @return a GameSettings object containing all settings
     */
    public GameSettings getSettings() {
        return settings;
    }


    public boolean getIsPrivate() {
        return password.equals("") ? false : true;
    }

    /**
     * This method gets the game of this room
     * @return Game, the game object of this room
     */
    public Game getGame(){
        return game;
    }

    /**
     * Gets the users that are present in this room.
     *
     * @return The list of users.
     */
    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
    }

    /**
     * This method is used to get the maximum amount of players allowed inside the room
     *
     * @return int  this returns the maximum amount of players allowed inside the room
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * This method is used to set the maximum amount of players allowed inside the room
     * @param value the new maximum amount of players allowed inside the room
     */
    public void setMaxPlayers(int value){
        maxPlayers = value;
    }

    public boolean getGameStarted(){
        return gameStarted;
    }

    public String[] getConnectData(){
        return new String[]{RMIConstants.GAME_SERVER_LOCATION, bindingName};
    }

    /**
     * Adds a user to the array list of users in the room
     *
     * @param user The user to add
     * @return a boolean value indicating success
     */
    @Override
    public boolean addUser(User user) throws RemoteException {
        if (users.size() < maxPlayers/* && !users.contains(user)*/) {
            users.add(user);
            user.setRoom(this);
            if (owner == null) {
                owner = user;
            }
            return true;
        }
        return false;
    }

    /**
     * Removes a user from the array list of users in the room
     * only removes if the user is present in list
     *
     * @param user The user to remove
     * @return True when the lobby should stay alive. False when the room should be removed.
     */
    @Override
    public boolean removeUser(User user) {
        for(User u: users){
            if (u.getID() == user.getID()) {
                users.remove(u);

                //If we just removed the owner assign it to someone else.
                if (users.isEmpty()) {
                    if (game != null) {
                        game.stop();
                    }
                    return false;
                } else if (owner.getID() == user.getID()) {
                    owner = users.get(0);
                }
                return true;
            }

        }
        return false;
    }

    private List<User> getAllHumanUsers() {
        return users.stream().filter(u -> !u.isComputer()).collect(Collectors.toList());
    }

    public void addAI() {
        User computer = new User(AI_NAMES.next());
        computer.turnIntoComputer();
        computer.setRoom(this);
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

    public boolean checkPassword(String pswd){
        if(password == ""){
            return true;
        }
        if(pswd.equals(password)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * This method is used to start the game
     */
    @Override
    public void startGame(String mapName) throws RemoteException{
/*        Game game = new Game(mapName);
        //game.getSettings(settings);
        ArrayList<User> randomList = new ArrayList<>(users);
        Collections.shuffle(randomList);

        game.addPlayer(new User("Trump")); //TODO TESTING CODE.

        for (User u : randomList) {
            game.addPlayer(u);
        }

        game.startGame();
        this.game = game;*/

        //TODO START SERVER
        // Connecting to gameServer with sockets
        try {
//            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "/out/artifacts/CrossTheBorder_jar/GameServer.jar");
//            pb.directory(new File("out/artifacts/server"));
//            Process process = pb.start();
//            if(process.isAlive()){
//                System.out.println("game server running");
//            }
            new Thread(() -> {
                try {
                    GameServer.main(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            //Process process = Runtime.getRuntime().exec("java -jar /out/artifacts/CrossTheBorder_jar2/GameServer.jar");
            Socket socket = new Socket(RMIConstants.GAME_SERVER_LOCATION, RMIConstants.SOCKET_PORT);

            try {
                // Creating output + input stream
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                // Put names in a list
                List<String> names = users.stream().map(User::getName).collect(Collectors.toList());

                // Sending names
                out.writeObject(names);

                // Getting bindingName
                bindingName = (String) in.readObject();

                // Sending mapname
                out.writeObject(mapName);
            } finally {
                socket.close();
            }
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (bindingName != null) {
            gameStarted = true;
        }

        //get ip, set gameStarted = true
        //clients get ip via getGameServerIp


    }
}