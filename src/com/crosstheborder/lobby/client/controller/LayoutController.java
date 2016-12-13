package com.crosstheborder.lobby.client.controller;

import com.crosstheborder.lobby.client.ClientMain;
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

    @FXML
    private void initialize(){

    }

    /**
     * This method is used for first time setup of the controller, if the initialize method cannot be used.
     */
    public void setUp(){

    }
    /**
     * Shows the lobby menu screen, see showRoomMenu in ClientMain.
     */
    public void showLobbyScreen(){
        instance.showRoomMenu();
    }

    /**
     * Shows the main screen, see showLobbyMenu in ClientMain.
     */
    public void showMainScreen(){
        instance.showLobbyMenu();
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
