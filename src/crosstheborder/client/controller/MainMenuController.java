package crosstheborder.client.controller;


import crosstheborder.client.ClientMain;
import crosstheborder.client.dialog.CreateLobbyDialog;
import crosstheborder.lib.Lobby;
import crosstheborder.lib.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.Optional;

/**
 * @author yannic
 * The controller class of the main menu
 */
public class MainMenuController {

    @FXML
    private Button joinLobbyButton;
    @FXML
    private Button createLobbyButton;
    @FXML
    private Button joinRandomLobbyButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Label playerNameLabel;
    @FXML
    private TableView lobbyTableView; //todo: add lobbies to tableView and the ability to join them.

    private ClientMain instance;
    private User user;
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
    }

    /**
     * Sets the text of the label used for displaying the name of the current user.
     * @param name the desired name.
     */
    public void setLblPlayerName(String name){
        playerNameLabel.setText(name);
    }

    @FXML
    private void btnCreateLobby_OnAction(){
        createLobby();
    }
    @FXML
    private void btnJoinLobby_OnAction(){
        //join a lobby

        throw new UnsupportedOperationException();
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

    private void showLobbyMenu() {
        instance.showLobbyMenu();
    }

    /**
     * Creates a new lobby and switches the view to the main menu
     */
    public void createLobby() {
        CreateLobbyDialog dialog = new CreateLobbyDialog();
        dialog.setTitle("Set lobby settings");
        dialog.setHeaderText("");
        Optional<List<String>> result = dialog.showAndWait();

        if (result.isPresent()) {
            String lobbyName = result.get().get(0);
            int maxPlayers = Integer.parseInt(result.get().get(1));

            Lobby lobby = new Lobby(user, lobbyName, maxPlayers);
            joinLobby(lobby);
        }
    }

    public void joinLobby(Lobby toJoin) {
        user.joinLobby(toJoin);
        showLobbyMenu();
    }

    public TableView getLobbyTableView() {
        return lobbyTableView;
    }
}
