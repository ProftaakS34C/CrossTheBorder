package com.crosstheborder.lobby.shared;

import crosstheborder.lib.Message;
import crosstheborder.lib.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Yannic
 * @author Guillaime
 */
public interface IRoom extends Remote{

    /**
     * This method gets a list of messages sent to this room.
     * @return a list of messages.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    List<Message> getMessages() throws RemoteException;

    /**
     * This method gets the users currently in the room.
     * @return a list of users.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    ArrayList<User> getUsers() throws RemoteException;

    /**
     * This method starts the game for everyone in the room.
     * @param mapName the name of the map to load.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    void startGame(String mapName) throws RemoteException;

    /**
     * Adds a message to the list of messages in this room.
     * @param msg the message to add.
     * @return true if succes.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    boolean addMessage(Message msg) throws RemoteException;

    /**
     * Adds a user to this room.
     * @param user the user to add.
     * @return true if succes.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    boolean addUser(User user) throws RemoteException;

    /**
     * Removes a user from this room.
     * @param user the user to remove.
     * @return true if succes.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    boolean removeUser(User user)throws RemoteException;

    /**
     * This method sets the password required to join the room, if none is set the room is considered public.
     * @param pswd the password to set.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    void setPassword(String pswd) throws RemoteException;

    /**
     * gets the user who is owner of this room.
     * @return a user.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    User getOwner() throws RemoteException;

    /**
     * Gets if the room is private, a room is private when there is no password set.
     * @return true if the room is private.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    boolean getIsPrivate() throws RemoteException;

    /**
     * Gets the name of this room.
     * @return the name of the room as a string.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    String getName() throws RemoteException;

    /**
     * Gets the max players allowed in this room.
     * @return an int value.
     * @throws RemoteException gets thrown when something goes worng remotely.
     */
    int getMaxPlayers() throws RemoteException;

    /**
     * This method is used for checking the password.
     * @param pswd the password to check if correct.
     * @return true if correct, false when not. Always returns true if no password is required.
     */
    boolean checkPassword(String pswd) throws RemoteException;

}
