package crosstheborder.ui.controller;

import crosstheborder.ui.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * @author Yannic
 * The controller class for the basic layout.
 */
public class LayoutController {

    @FXML
    private void initialize(){

    }

    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem testMainMenuItem;
    @FXML
    private MenuItem testLobbyMenuItem;
    @FXML
    private MenuItem testGameMenuItem;

    /**
     * Shows the lobby menu screen, see showLobbyMenu in ClientMain.
     */
    public void showLobbyScreen(){
        ClientMain.getInstance().showLobbyMenu();
    }

    /**
     * Shows the main screen, see showMainMenu in ClientMain.
     */
    public void showMainScreen(){
        ClientMain.getInstance().showMainMenu();
    }

    /**
     * Shows the game screen, see showGameScreen in ClientMain.
     */
    public void showGameScreen(){
        ClientMain.getInstance().showGameScreen();
    }
}
