package crosstheborder.client;

/**
 * @author Oscar
 * @autor Yannic
 *
 */

import crosstheborder.client.controller.GameScreenController;
import crosstheborder.client.controller.LayoutController;
import crosstheborder.client.controller.LobbyMenuController;
import crosstheborder.client.controller.MainMenuController;
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

        showMainMenu();
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
    public void showMainMenu(){
        Pane menuRoot;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/MainMenu.fxml"));
            menuRoot = loader.load();
            MainMenuController controller = loader.getController();
            controller.setUp(this);
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
    public void showLobbyMenu(){
        Pane lobbyRoot;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/LobbyMenu.fxml"));
            lobbyRoot = loader.load();
            LobbyMenuController controller = loader.getController();
            controller.setUp(this);
            root.setCenter(lobbyRoot);
            primaryStage.setTitle("Lobby menu");
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
            primaryStage.setTitle("In Game");
        }
        catch (IOException x){
            System.err.println("Could not load game screen FXML.");
            LOGGER.log(Level.SEVERE, x.toString(), x);
        }
    }
}
