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

    private ClientMain instance;

    /**
     * Shows the lobby menu screen, see showLobbyMenu in ClientMain.
     */
    public void showLobbyScreen(){
        instance.showLobbyMenu();
    }

    /**
     * Shows the main screen, see showMainMenu in ClientMain.
     */
    public void showMainScreen(){
        instance.showMainMenu();
    }

    /**
     * Shows the game screen, see showGameScreen in ClientMain.
     */
    public void showGameScreen(){
        instance.showGameScreen();
    }

    /**
     * Sets the main class this controller uses for functions
     * @param instance the ClientMain class
     */
    public void setInstance(ClientMain instance) {
        this.instance = instance;
    }
}
