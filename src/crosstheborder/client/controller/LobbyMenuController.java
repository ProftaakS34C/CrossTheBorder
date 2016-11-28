package crosstheborder.client.controller;


import crosstheborder.client.ClientMain;
import crosstheborder.client.LobbyPuller;
import crosstheborder.client.dialog.CreateLobbyDialog;
import crosstheborder.server.Lobby;
import crosstheborder.lib.User;
import crosstheborder.shared.ILobby;
import crosstheborder.shared.IRoom;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.rmi.RemoteException;
import java.util.ArrayList;
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
    private TableView<IRoom> roomTableView; //todo: add lobbies to tableView and the ability to join them.

    private ClientMain instance;
    private User user;

    private Timer pullerTimer;
    private ILobby lobby;
    //TODO keep a list of lobbies. Should be fetching from the server.

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

    public void setLobby(ILobby lobby) {
        this.lobby = lobby;
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
     * Creates a new lobby and switches the view to the main menu
     */
    @FXML
    public void createRoom() {
        CreateLobbyDialog dialog = new CreateLobbyDialog();
        dialog.setTitle("Create Room");
        dialog.setHeaderText("");
        Optional<List<String>> result = dialog.showAndWait();

        //TODO Create room on server
    }

    @FXML
    public void joinRoom() {
        IRoom room = roomTableView.getSelectionModel().getSelectedItem();
        user.joinLobby(room);
        instance.showRoomMenu();
    }

    @FXML
    public void refreshRoomTableView() {
        roomTableView.getItems().clear();

        TableColumn nameColumn = roomTableView.getColumns().get(0);
        TableColumn userAmountColumn = roomTableView.getColumns().get(1);
        TableColumn privateColumn = roomTableView.getColumns().get(2);

        nameColumn.setCellValueFactory(new PropertyValueFactory<IRoom, String>("name"));
        userAmountColumn.setCellValueFactory(new PropertyValueFactory<IRoom, String>("userAmount"));
        privateColumn.setCellValueFactory(new PropertyValueFactory<IRoom, String>("isPrivate"));

        try {
            for (IRoom room : lobby.getRooms()) {
                roomTableView.getItems().add(room);
            }

        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

    }
}
