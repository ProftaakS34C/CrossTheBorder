package com.crosstheborder.lobby.client;

/**
 * @author Oscar
 * @author Yannic
 *
 */

import com.crosstheborder.lobby.client.controller.GameScreenController;
import com.crosstheborder.lobby.client.controller.LayoutController;
import com.crosstheborder.lobby.client.controller.RoomMenuController;
import com.crosstheborder.lobby.client.controller.LobbyMenuController;
import com.crosstheborder.lobby.shared.ILobby;
import com.crosstheborder.lobby.shared.IRoom;
import crosstheborder.lib.Map;
import crosstheborder.lib.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class of the application
 */
public class ClientMain extends Application {

    private static final Logger LOGGER = Logger.getLogger(Map.class.getName());

    private Stage primaryStage;
    private BorderPane root;
    private User user;
    private ILobby lobby;
    private String ipAddress = "localhost";
    private Registry registry;
    private static final String bindingName = "lobby";

    /**
     * The main method for the class
     *
     * @param args not used.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method of main, loads the basic layout and shows the main menu on it.
     *
     * @param primaryStage the stage to load the scenes on
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        initLayout();
        String userName = askForUserName();
        this.user = new User(userName);
        connect();
        try {
            user.setID(lobby.addUser(user));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        showLobbyMenu();
    }

    private void connect() {
        try{
            InetAddress serverHost = InetAddress.getLocalHost();
            ipAddress = serverHost.getHostAddress();
        } catch (UnknownHostException ex) {
            System.out.println("Cannot get IP address of server: " + ex.getMessage());
        }

        // Locate registry
        try{
            registry = LocateRegistry.getRegistry(ipAddress, 1099);
            System.out.println("Registry found");
        } catch (RemoteException e) {
            System.out.println("Cannot locate registry");
            registry = null;
        }
        if(registry != null){
            try {
                lobby = (ILobby) registry.lookup(bindingName);
            } catch (RemoteException e) {
                lobby = null;
                e.printStackTrace();
            } catch (NotBoundException e) {
                lobby = null;
                e.printStackTrace();
            } catch (Exception ex) {
                System.out.println("Failed Getting lobby!");
            }
        }
    }

    /**
     * This method gets the user for this client
     *
     * @return A User object
     */
    public User getUser() {
        return user;
    }

    /**
     * This method opens a dialog window asking for a string value
     * @return A String representing the (nick)name of the user
     */
    private String askForUserName(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Enter your nickname: ");
        dialog.setTitle("Enter your nickname.");
        dialog.setHeaderText("");

        String userName;
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent() && !result.get().isEmpty()){
            userName = result.get();
        }
        else{
            Alert popup = new Alert(Alert.AlertType.ERROR);
            popup.setContentText("You need to enter a username to play this game");
            popup.setHeaderText("No name found");
            popup.setTitle("Error");
            popup.showAndWait();
            userName = askForUserName();
        }
        return userName;
    }

    /**
     * loads the layout into the stage
     */
    public void initLayout(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/Layout.fxml"));
            root = loader.load();
            LayoutController controller = loader.getController();
            controller.setInstance(this);
            controller.setUp();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Could not load layout FXML.");
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * loads the main menu into the layout
     */
    public void showLobbyMenu(){
        Pane menuRoot;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/LobbyMenu.fxml"));
            menuRoot = loader.load();
            LobbyMenuController controller = loader.getController();
            controller.setUp(this);
            controller.setLobby(lobby);
            root.setCenter(menuRoot);
            primaryStage.setTitle("Main Menu");
        }
        catch (IOException x){
            System.err.println("Could not load main menu FXML.");
            LOGGER.log(Level.SEVERE, x.toString(), x);
        }
    }

    /**
     * loads the lobby menu onto the layout
     */
    public void showRoomMenu(){
        Pane lobbyRoot;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/RoomMenu.fxml"));
            lobbyRoot = loader.load();
            RoomMenuController controller = loader.getController();
            controller.setUp(this);
            root.setCenter(lobbyRoot);
            primaryStage.setTitle("Room menu");
        }
        catch (IOException x){
            System.err.println("could not load lobby menu fxml");
            LOGGER.log(Level.SEVERE, x.toString(), x);
        }
    }

    /**
     * shows the game screen
     */
    public void showGameScreen(){
        //load the game fxml file.
        Canvas gameRoot;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/GameScreen.fxml"));
            gameRoot = loader.load();
            GameScreenController controller = loader.getController();
            controller.setUp(this);
            root.setCenter(gameRoot);
            gameRoot.requestFocus();
            primaryStage.setTitle("In Game");
        }
        catch (IOException x){
            System.err.println("Could not load game screen FXML.");
            LOGGER.log(Level.SEVERE, x.toString(), x);
        }
    }
}
