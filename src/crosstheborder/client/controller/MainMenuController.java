package crosstheborder.client.controller;



import crosstheborder.client.ClientMain;
import crosstheborder.client.dialog.CreateLobbyDialog;
import crosstheborder.lib.Lobby;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.util.ArrayList;
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
    private TableView lobbyTableView;

    private ClientMain instance;

    @FXML
    private void initialize(ClientMain instance){
        //constructor type stuff

    }
    /**
     * This method is used for first time setup of the controller, if the initialize method cannot be used.
     */
    public void setUp(){

        setLblPlayerName(instance.getUser().getName());
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
        CreateLobbyDialog dialog = new CreateLobbyDialog();
        dialog.setTitle("set lobby settings");
        dialog.setHeaderText("");
        Optional<List<String>> result = dialog.showAndWait();
        if(result.isPresent()){
            ArrayList<String> lobbyList = (ArrayList<String>) result.get();
            Lobby lobby = new Lobby(instance.getUser(), lobbyList.get(0), Integer.parseInt(lobbyList.get(1)));
            instance.getUser().setLobby(lobby);
            instance.showLobbyMenu();
        }
        else {
            return;
        }
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

    public TableView getLobbyTableView() {
        return lobbyTableView;
    }

    /**
     * Sets the main class this controller uses for functions
     * @param instance the ClientMain class
     */
    public void setInstance(ClientMain instance){
        this.instance = instance;
    }


}
