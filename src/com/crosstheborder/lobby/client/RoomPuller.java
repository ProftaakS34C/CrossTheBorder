package com.crosstheborder.lobby.client;

import com.crosstheborder.lobby.client.controller.LobbyMenuController;
import com.crosstheborder.lobby.client.controller.RoomMenuController;
import javafx.application.Platform;

import java.util.TimerTask;

/**
 * Created by Yannic on 4-12-2016.
 */
public class RoomPuller extends TimerTask {

    private RoomMenuController controller;

    public RoomPuller(RoomMenuController controller){

        this.controller = controller;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            controller.refreshChatListView();
            controller.refreshUsersTableView();
            controller.checkForGameStart();
        });
    }
}
