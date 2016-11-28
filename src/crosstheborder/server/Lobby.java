package crosstheborder.server;

import crosstheborder.lib.User;
import crosstheborder.shared.ILobby;
import crosstheborder.shared.IRoom;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by guill on 22-11-2016.
 */
public class Lobby extends UnicastRemoteObject implements ILobby{

    private ArrayList<IRoom> rooms;

    public Lobby() throws RemoteException {
        this.rooms = new ArrayList<>();
    }

    @Override
    public ArrayList<IRoom> getRooms() throws RemoteException{
        return this.rooms;
    }

    @Override
    public String getName() throws RemoteException {
        return null;
    }

    @Override
    public void createRoom(String name) throws RemoteException{
        rooms.add(new Room(new User("Henkie"), name, 8));
    }

    @Override
    public void joinRoom(Room room) throws RemoteException{
        System.out.println("Joining room");
    }

    @Override
    public void addUser(User user) throws RemoteException{
        System.out.println("Adding User");
    }
}
