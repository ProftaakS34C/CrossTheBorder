package com.crosstheborder.lobby.client;

import com.crosstheborder.lobby.client.controller.LobbyMenuController;
import com.crosstheborder.lobby.shared.ILobby;
import javafx.application.Platform;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.TimerTask;

/**
 * @author Yannic
 * @author Guillaime
 */
public class LobbyPuller extends TimerTask {

    private LobbyMenuController controller;
    public String ipAddress = "localhost"; //should be server ip
    private static int portNumber = 1099;

    private static final String bindingName = "lobby";

    private Registry registry = null;
    private ILobby lobby = null;

    public LobbyPuller(LobbyMenuController controller){

        try{
            InetAddress serverhost = InetAddress.getLocalHost();
            ipAddress = serverhost.getHostAddress();
        } catch (UnknownHostException ex) {
            System.out.println("Cannot get IP address of server: " + ex.getMessage());
        }

        this.controller = controller;

        // Locate registry
        try{
            registry = LocateRegistry.getRegistry(ipAddress, 1099);
            System.out.println("Registry found");
        } catch (RemoteException e) {
            System.out.println("Cannot locate registry");
            registry = null;
        }
    }

    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    if(registry != null){
                        try {
                            lobby = (ILobby) registry.lookup(bindingName);
                        } catch (RemoteException e) {
                            lobby = null;
                        } catch (NotBoundException e) {
                            lobby = null;
                        }
                    }
//                    if(!controller.getLobby().equals(lobby)){
                        controller.setLobby(lobby);
                        controller.refreshRoomTableView();
//                    }


                } catch (Exception ex){
                    System.out.println("Failed Getting lobby!");
                }
            }
        });
    }
}
