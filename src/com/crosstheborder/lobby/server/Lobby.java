package com.crosstheborder.lobby.server;

import crosstheborder.lib.User;
import com.crosstheborder.lobby.shared.ILobby;
import com.crosstheborder.lobby.shared.IRoom;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * @author Yannic
 * @author Guillaime
 */
public class Lobby extends UnicastRemoteObject implements ILobby{

    private ArrayList<IRoom> rooms;
    private ArrayList<User> users;

    public Lobby() throws RemoteException {
        this.rooms = new ArrayList<>();
    }

    @Override
    public ArrayList<IRoom> getRooms() throws RemoteException{
        return this.rooms;
    }


    @Override
    public void createRoom(String name, User creator) throws RemoteException{
        rooms.add(new Room(creator, name, 8));

    }

    @Override
    public void joinRoom(Room room, User user) throws RemoteException{
        System.out.println("Joining room");
        room.addUser(user);

    }

    @Override
    public void addUser(User user) throws RemoteException{
        System.out.println("Adding User");
    }
}
