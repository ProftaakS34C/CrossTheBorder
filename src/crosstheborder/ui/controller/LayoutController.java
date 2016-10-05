package crosstheborder.ui.controller;

import crosstheborder.ui.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Created by yannic on 04/10/2016.
 */
public class LayoutController {

    public void initialize(){

    }

    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;
    @FXML
    private MenuItem menuItemClose;
    @FXML
    private MenuItem testMain;
    @FXML
    private MenuItem testLobby;
    @FXML
    private MenuItem testGame;

    public void testLobbyAction(){
        ClientMain.getInstance().showLobbyMenu();
    }

    public void testMainAction(){
        ClientMain.getInstance().showMainMenu();
    }

    public void showGameScreen(){
        ClientMain.getInstance().showGameScreen();
    }
}
