package crosstheborder.shared;

import crosstheborder.lib.User;
import crosstheborder.server.Room;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by guill on 22-11-2016.
 */
public interface ILobby extends Remote {

    void createRoom(String name) throws RemoteException;
    void joinRoom(Room room) throws RemoteException;
    void addUser(User user) throws RemoteException;
    ArrayList<IRoom> getRooms() throws RemoteException;
    String getName() throws RemoteException;


}
