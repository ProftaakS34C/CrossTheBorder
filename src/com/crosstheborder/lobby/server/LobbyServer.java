package com.crosstheborder.lobby.server;

import com.crosstheborder.lobby.shared.User;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author guillaime
 */
public class LobbyServer {

    private static final int portNumber = 1099;
    private static final String bindingName = "lobby";
    private Registry registry = null;
    private Lobby lobby = null;

    public LobbyServer(){

        // Getting IP of server
        try{
            InetAddress serverhost = InetAddress.getLocalHost();
            System.out.println("IP Address: " + serverhost.getHostAddress());
        } catch (UnknownHostException ex) {
            System.out.println("Cannot get IP address of server: " + ex.getMessage());
        }

        // Port number
        System.out.println(portNumber);

        // Creating lobby
        try{
            lobby = new Lobby();
            User henk = new User("henk1");
            henk.setID(lobby.addUser(henk));
            lobby.createRoom("Test room", 8, henk);
//            lobby.createRoom("Kekkerdekek",8 , new User("henk2"));
//            lobby.createRoom("Bla bla lbagsdgsg",8 , new User("henk3"));
//            lobby.createRoom("Test 2",8 , new User("henk4"));
//            lobby.createRoom("Test 3",8 , new User("henk5"));
            System.out.println("Lobby created!");
        } catch (RemoteException e) {
            System.out.println("Cannot create lobby: " + e.getMessage());
        }

        // Create registry
        try {
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Registry created!");
        } catch (RemoteException ex) {
            System.out.println("Cannot create registry: " + ex.getMessage());
            registry = null;
        }

        // Bind effectenbeurs using registry
        try{
            registry.rebind(bindingName, lobby);
            System.out.println("lobby bound to registry");
        } catch (RemoteException ex) {
            System.out.println("Cannot bind lobby to registry: " + ex.getMessage());
        }
    }

    public static void main(String[] args){
        System.out.println("CREATING SERVER");
        LobbyServer server = new LobbyServer();
    }
}
