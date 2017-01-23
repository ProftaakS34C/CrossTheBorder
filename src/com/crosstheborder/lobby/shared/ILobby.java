package com.crosstheborder.lobby.shared;

import com.crosstheborder.lobby.server.Room;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author Yannic
 * @author Guillaime
 */
public interface ILobby extends Remote {

    /**
     * This method creates a room in the lobby for people to join.
     * @param name the name of the room to create.
     * @param creator the user that created the room.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    IRoom createRoom(String name, int maxPlrs, User creator) throws RemoteException;

    /**
     * This method lets a user join an existing room, if the room does not exist nothing happens.
     * @param room the room to join.
     * @param user the user joining.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    void joinRoom(Room room, User user) throws RemoteException;

    /**
     * This method adds a user to the lobby.
     * It also sets the id of the user on the server and returns the id to set on the client.
     * @param user the user to join the Lobby.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    int addUser(User user) throws RemoteException;

    /**
     * This method gets all the rooms present in the lobby
     * @return a list of rooms.
     * @throws RemoteException gets thrown when something goes wrong remotely.
     */
    ArrayList<IRoom> getRooms() throws RemoteException;

}
