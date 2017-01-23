package com.crosstheborder.lobby.client.controller;


import com.crosstheborder.game.client.GameClient;
import com.crosstheborder.lobby.client.ClientMain;
import com.crosstheborder.lobby.client.RoomPuller;
import com.crosstheborder.lobby.shared.Message;
import com.crosstheborder.lobby.shared.User;
import com.crosstheborder.lobby.shared.IRoom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.Timer;

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
    private TextField mapNameInputTextField; //TODO POLISH change into choicebox with all available maps
    @FXML
    private Label labelMapName;

    private ClientMain instance;
    private IRoom room;
    private User user;
    private Timer pullTimer;
    private boolean hasStarted;

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

        if (!user.isOwnerOfLobby()) {
            startGameButton.setVisible(false);
            lobbyPassInputPasswordField.setVisible(false);
            isPrivateCheckBox.setVisible(false);
            mapNameInputTextField.setVisible(false);
            labelMapName.setVisible(false);
        }
        pullTimer = new Timer();
        pullTimer.scheduleAtFixedRate(new RoomPuller(this), 0,5000);
    }

    @FXML
    private void isPrivateCheckBox_OnAction(ActionEvent event){
        try{
            if(isPrivateCheckBox.isSelected()){
                if (!lobbyPassInputPasswordField.getText().trim().equals("") && lobbyPassInputPasswordField.getText() != null) {
                    lobbyPassInputPasswordField.setVisible(false);
                    room.setPassword(lobbyPassInputPasswordField.getText());
                }
                else {
                    isPrivateCheckBox.setSelected(false);
                }
            }
            else {
                lobbyPassInputPasswordField.setText("");
                room.setPassword("");
                lobbyPassInputPasswordField.setVisible(true);
            }
        } catch (RemoteException e){
            e.printStackTrace();
        }

    }

    @FXML
    private void leaveLobbyButton_OnAction(){
        leaveRoom();
    }

    @FXML
    private void btnStartGame_OnAction(){

        String mapName = mapNameInputTextField.getText();

        try {
            room.startGame(mapName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

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

    public void refreshChatListView() {
        chatListView.getItems().clear();
        try {
            for (Message message : room.getMessages()) {
                chatListView.getItems().add(message);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void refreshUsersTableView() {
        usersTableView.getItems().clear();

        TableColumn nameColumn = usersTableView.getColumns().get(0);
        TableColumn ownerColumn = usersTableView.getColumns().get(1);
        TableColumn computerColumn = usersTableView.getColumns().get(2);

        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<User, Boolean>("owner"));
        //computerColumn.setCellValueFactory(new PropertyValueFactory<User, Boolean>("computer"));

        try {
            for (User user : room.getUsers()) {
                usersTableView.getItems().add(user);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void checkForGameStart() {
        try{
            if(room.getGameStarted() && !hasStarted){
                String[] connectData = room.getConnectData();
                String[] gameClientStartup = new String[]{connectData[0], connectData[1], user.getName()};
                pullTimer.purge();
                runGame(gameClientStartup);
                hasStarted = true;

            }
        }catch (RemoteException e){
            e.printStackTrace();
        }

    }

    public void runGame(String[] b) {
        //new Thread(() -> {
            try {
                //Runtime runtime = Runtime.getRuntime();
                //runtime.exec("out/artifacts/CrossTheBorder_jar2/GameClient.jar");
                //Platform.runLater(() ->  GameClient.main(b));
                //Application.launch(GameClient.class, b);
                GameClient gc = new GameClient();
                gc.setup(b);
                gc.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        //}).start();
    }

}
