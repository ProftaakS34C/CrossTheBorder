package com.crosstheborder.lobby.client.controller;


import com.crosstheborder.lobby.client.ClientMain;
import com.crosstheborder.lobby.client.LobbyPuller;
import com.crosstheborder.lobby.client.UIRoom;
import com.crosstheborder.lobby.client.dialog.CreateLobbyDialog;
import crosstheborder.lib.User;
import com.crosstheborder.lobby.shared.ILobby;
import com.crosstheborder.lobby.shared.IRoom;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TableView<UIRoom> roomTableView;

    private ClientMain instance;
    private User user;

    private Timer pullerTimer;
    private ILobby lobby;

    /**
     * This method is used for first time setup of the controller, if the initialize method cannot be used.
     * Sets the main class this controller uses for functions.
     *
     * @param instance the ClientMain class
     */
    public void setUp(ClientMain instance) {
        this.instance = instance;
        this.user = instance.getUser();
        setLblPlayerName(user.getName() + user.getID());

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
        try {
            IRoom room = lobby.createRoom(name, maxPlrs, this.user);
            user.setRoom(room);
            instance.showRoomMenu();
        } catch (RemoteException e) {
            //Todo Add logger to this controller
            e.printStackTrace();
        }

    }

    @FXML
    public void joinRoom() {
        IRoom room = roomTableView.getSelectionModel().getSelectedItem().getRoom();

        try {
            if(room.getIsPrivate()){
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("password required");
                dialog.setHeaderText("A password is required for this room");
                dialog.setContentText("Please enter a password:");

                Optional<String> result = dialog.showAndWait();
                if(result.isPresent()){
                    String pswd = result.get();
                    if(!room.checkPassword(pswd)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("oops");
                        alert.setHeaderText(null);
                        alert.setContentText("Wrong password!");
                        alert.show();
                        return;
                    }
                }
            }
            if(room.addUser(user)){
                user.setRoom(room);
                instance.showRoomMenu();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The room you tried to join is full");
                alert.show();
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }


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
            //todo lobby.getRooms vervangen met een field : List<IRoom> rooms, poll doet rooms = lobby.getRooms
            for (IRoom room : lobby.getRooms()) {
                UIRoom uiRoom = new UIRoom(room);
                roomTableView.getItems().add(uiRoom);
            }

        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

    }
}
