package com.crosstheborder.lobby.client.controller;


import com.crosstheborder.lobby.client.ClientMain;
import crosstheborder.lib.Message;
import crosstheborder.lib.User;
import com.crosstheborder.lobby.shared.IRoom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.rmi.RemoteException;

/**
 * The controller class of the room menu
 *
 * @author Yannic
 * @author Oscar de Leeuw
 */
public class RoomMenuController {

    @FXML
    private Button startGameButton;
    @FXML
    private ListView<Message> chatListView;
    @FXML
    private TableView<User> usersTableView;
    @FXML
    private CheckBox isPrivateCheckBox;
    @FXML
    private PasswordField lobbyPassInputPasswordField;
    @FXML
    private TextField chatInputTextField;
    @FXML
    private Button leaveLobbyButton;
    @FXML
    private Button addAiButton;
    @FXML
    private TextField mapNameInputTextField;

    private ClientMain instance;
    private IRoom room;
    private User user;

    /**
     * This method is used for first time setup of the controller, if the initialize method cannot be used.
     * Sets the main class this controller uses for functions
     *
     * @param instance the ClientMain class
     */
    public void setUp(ClientMain instance) {
        this.instance = instance;
        this.user = instance.getUser();
        this.room = this.user.getRoom();
        //todo update room from server using polling(?)

        if (!user.isOwnerOfLobby()) {
            startGameButton.setVisible(false);
            lobbyPassInputPasswordField.setVisible(false);
            isPrivateCheckBox.setVisible(false);
//            addAiButton.setVisible(false);
        }

        refreshUsersTableView();
        refreshChatListView();
    }

    @FXML
    private void textBoxIsPrivate_OnAction(ActionEvent event){
        if(isPrivateCheckBox.isSelected()){
            if (!lobbyPassInputPasswordField.getText().trim().equals("") && lobbyPassInputPasswordField.getText() != null) {
                lobbyPassInputPasswordField.setVisible(false);
                try {
                    room.setPassword(lobbyPassInputPasswordField.getText());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            else {
                isPrivateCheckBox.setSelected(false);
            }
        }
        else {
            lobbyPassInputPasswordField.setText("");
            try {
                room.setPassword("");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            lobbyPassInputPasswordField.setVisible(true);
        }
    }

    @FXML
    private void leaveLobbyButton_OnAction(){
        leaveRoom();
    }

    @FXML
    private void btnStartGame_OnAction(){
        //TODO POLISH change into choicebox with all available maps
        String mapName = mapNameInputTextField.getText();

        try {
            room.startGame(mapName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        instance.showGameScreen();
    }

    @FXML
    private void btnChat_OnAction(){
        String chatText = chatInputTextField.getText();

            Message message = new Message(user, chatText);
            try {
                room.addMessage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        chatInputTextField.clear();
        refreshChatListView();
    }

    /**
     * Leaves the current room.
     */
    private void leaveRoom() {

        try {
            room.removeUser(user);
            user.leaveRoom();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        instance.showLobbyMenu();
    }

    private void refreshChatListView() {
        chatListView.getItems().clear();
        try {
            for (Message message : room.getMessages()) {
                chatListView.getItems().add(message);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void refreshUsersTableView() {
        usersTableView.getItems().clear();

        TableColumn nameColumn = usersTableView.getColumns().get(0);
        TableColumn ownerColumn = usersTableView.getColumns().get(1);
        TableColumn computerColumn = usersTableView.getColumns().get(2);

        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<User, Boolean>("owner"));
        computerColumn.setCellValueFactory(new PropertyValueFactory<User, Boolean>("computer"));

        try {
            for (User user : room.getUsers()) {
                usersTableView.getItems().add(user);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
