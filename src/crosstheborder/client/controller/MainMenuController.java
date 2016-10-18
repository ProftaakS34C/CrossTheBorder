package crosstheborder.client.controller;


import crosstheborder.client.ClientMain;
import crosstheborder.client.dialog.CreateLobbyDialog;
import crosstheborder.lib.Lobby;
import crosstheborder.lib.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

/**
 * @author yannic
 * The controller class of the main menu
 */
public class MainMenuController {

    @FXML
    private Label playerNameLabel;
    @FXML
    private TableView<Lobby> lobbyTableView; //todo: add lobbies to tableView and the ability to join them.

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
        refreshLobbyTableView();
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

    private void showLobbyMenu() {
        instance.showLobbyMenu();
    }

    /**
     * Creates a new lobby and switches the view to the main menu
     */
    @FXML
    public void createLobby() {
        CreateLobbyDialog dialog = new CreateLobbyDialog();
        dialog.setTitle("Create Lobby");
        dialog.setHeaderText("");
        Optional<List<String>> result = dialog.showAndWait();

        if (result.isPresent()) {
            String lobbyName = result.get().get(0);
            int maxPlayers = Integer.parseInt(result.get().get(1));

            Lobby lobby = new Lobby(user, lobbyName, maxPlayers);
            instance.showLobbyMenu();
        }
    }

    @FXML
    public void joinLobby() {
        Lobby lobby = lobbyTableView.getSelectionModel().getSelectedItem();
        user.joinLobby(lobby);
        instance.showLobbyMenu();
    }

    @FXML
    public void refreshLobbyTableView() {
        lobbyTableView.getItems().clear();

        TableColumn nameColumn = lobbyTableView.getColumns().get(0);
        TableColumn userAmountColumn = lobbyTableView.getColumns().get(1);
        TableColumn privateColumn = lobbyTableView.getColumns().get(2);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Lobby, String>("name"));
        userAmountColumn.setCellValueFactory(new PropertyValueFactory<Lobby, String>("userAmount"));
        privateColumn.setCellValueFactory(new PropertyValueFactory<Lobby, String>("isPrivate"));

        for (Lobby lobby : instance.getLobbies()) {
            lobbyTableView.getItems().add(lobby);
        }
    }
}
