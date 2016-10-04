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

public class MainMenuController {

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

    public void testButtonMethod(){
        System.out.println("button pressed");
    }

    public void setLblPlayerName(String name){
        labelSpelerNaam.setText(name);
    }


}
