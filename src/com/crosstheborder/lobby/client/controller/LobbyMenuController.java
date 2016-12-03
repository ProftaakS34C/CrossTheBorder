package com.crosstheborder.lobby.client.controller;


import com.crosstheborder.lobby.client.ClientMain;
import com.crosstheborder.lobby.client.LobbyPuller;
import com.crosstheborder.lobby.client.UIRoom;
import com.crosstheborder.lobby.client.dialog.CreateLobbyDialog;
import crosstheborder.lib.User;
import com.crosstheborder.lobby.shared.ILobby;
import com.crosstheborder.lobby.shared.IRoom;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.Timer;

/**
 * @author yannic
 * The controller class of the main menu
 */
public class LobbyMenuController {

    @FXML
    private Label playerNameLabel;
    @FXML
    private TableView<UIRoom> roomTableView; //todo: add the ability to join rooms.

    private ClientMain instance;
    private User user;

    private Timer pullerTimer;
    private ILobby lobby;
    //TODO keep a list of rooms. Should be fetching from the server.

    /**
     * This method is used for first time setup of the controller, if the initialize method cannot be used.
     * Sets the main class this controller uses for functions.
     *
     * @param instance the ClientMain class
     */
    public void setUp(ClientMain instance) {
        this.instance = instance;
        this.user = instance.getUser();
        setLblPlayerName(user.getName());

        pullerTimer = new Timer();
        pullerTimer.scheduleAtFixedRate(new LobbyPuller(this), 0, 5000);
    }

    /**
     * Sets the lobby for the controller
     * @param lobby an interface of Lobby
     */
    public void setLobby(ILobby lobby) {
        this.lobby = lobby;
    }

    /**
     * Gets the lobby of this controller
     * @return an interface of Lobby
     */
    public ILobby getLobby(){
        return lobby;
    }
    /**
     * Sets the text of the label used for displaying the name of the current user.
     * @param name the desired name.
     */
    public void setLblPlayerName(String name){
        playerNameLabel.setText(name);
    }

    @FXML
    private void btnJoinRandomLobby_OnAction(){
        //join a random lobby
        throw new UnsupportedOperationException();
    }
    @FXML
    private void btnSettings_OnAction(){
        //open settings
        throw new UnsupportedOperationException();
    }

    private void showRoomMenu() {
        instance.showRoomMenu();
    }

    /**
     * Creates a new Room and switches the view to the main menu
     */
    @FXML
    public void createRoom() {
        CreateLobbyDialog dialog = new CreateLobbyDialog();
        dialog.setTitle("Create Room");
        dialog.setHeaderText("");
        Optional<List<String>> result = dialog.showAndWait();

        List<String> roomStrings = result.get();
        String name = roomStrings.get(0);
        int maxPlrs = Integer.parseInt(roomStrings.get(1));
        //TODO Create room on server
        try {
            IRoom room = lobby.createRoom(name, maxPlrs, this.user);
            user.joinRoom(room);
            instance.showRoomMenu();
        } catch (RemoteException e) {
            //Todo Add logger to this controller
            e.printStackTrace();
        }

    }

    @FXML
    public void joinRoom() {
        IRoom room = roomTableView.getSelectionModel().getSelectedItem().getRoom();

        user.joinRoom(room);

        instance.showRoomMenu();
    }

    @FXML
    public void refreshRoomTableView() {
        roomTableView.getItems().clear();

        TableColumn nameColumn = roomTableView.getColumns().get(0);
        TableColumn userAmountColumn = roomTableView.getColumns().get(1);
        TableColumn privateColumn = roomTableView.getColumns().get(2);

        nameColumn.setCellValueFactory(new PropertyValueFactory<UIRoom, String>("name"));
        userAmountColumn.setCellValueFactory(new PropertyValueFactory<UIRoom, String>("userAmount"));
        privateColumn.setCellValueFactory(new PropertyValueFactory<UIRoom, String>("isPrivate"));

        try {
            for (IRoom room : lobby.getRooms()) {
                UIRoom uiRoom = new UIRoom(room);
                roomTableView.getItems().add(uiRoom);
            }

        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

    }
}
