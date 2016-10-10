package crosstheborder.client.controller;



import crosstheborder.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

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
        //do something
        throw new UnsupportedOperationException();
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
