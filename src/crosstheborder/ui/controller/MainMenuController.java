package crosstheborder.ui.controller;

/**
 * Created by yannic on 03/10/2016.
 */

import crosstheborder.ui.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MainMenuController {

    @FXML
    public void initialize(){
        //constructor type stuff

        //setLblPlayerName(ClientMain.getInstance().getUser().getName());
    }

    @FXML
    private Button btnJoinLobby;
    @FXML
    private Button btnCreateLobby;
    @FXML
    private Button btnJoinRandomLobby;
    @FXML
    private Button btnSettings;
    @FXML
    private Label labelSpelerNaam;
    @FXML
    private TableView lobbyTable;

/*    public void testButtonMethod(){
        System.out.println("button pressed");
    }*/

    public void setLblPlayerName(String name){
        labelSpelerNaam.setText(name);
    }
    @FXML
    private void btnCreateLobby_OnAction(){
        //do something
        throw new NotImplementedException();
    }
    @FXML
    private void btnJoinLobby_OnAction(){
        //join a lobby
        throw new NotImplementedException();
    }
    @FXML
    private void btnJoinRandomLobby_OnAction(){
        //join a random lobby
        throw new NotImplementedException();
    }
    @FXML
    private void btnSettings_OnAction(){
        //open settings
        throw new NotImplementedException();
    }

    public TableView getLobbyTable() {
        return lobbyTable;
    }
}
