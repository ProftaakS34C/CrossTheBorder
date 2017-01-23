package com.crosstheborder.lobby.server;

import com.crosstheborder.lobby.shared.User;
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

    private ArrayList<Room> rooms;
    private ArrayList<User> users;
    private int userIDCounter = 1;

    public Lobby() throws RemoteException {
        this.rooms = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    @Override
    public ArrayList<IRoom> getRooms() throws RemoteException{
        removeDeadRooms();
        return new ArrayList<IRoom>(rooms);
    }


    private int getNewID() {
        return userIDCounter++;
    }

    private void removeDeadRooms(){
        for(Room r : rooms){
            if(r.getUsers().size() == 0){
                rooms.remove(r);
            }
        }
    }


    @Override
    public IRoom createRoom(String name, int maxPlrs, User creator) throws RemoteException{
        Room room = new Room(creator, name, maxPlrs);
        rooms.add(room);
        return room;
    }

    @Override
    public void joinRoom(Room room, User user) throws RemoteException{
        System.out.println("Joining room");
        room.addUser(user);

    }

    @Override
    public int addUser(User user) throws RemoteException{
        user.setID(getNewID());
        users.add(user);
        return user.getID();
    }
}
