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

    public LobbyPuller(LobbyMenuController controller){

        this.controller = controller;
    }

    @Override
    public void run() {
        Platform.runLater(() -> controller.refreshRoomTableView());
    }
}
