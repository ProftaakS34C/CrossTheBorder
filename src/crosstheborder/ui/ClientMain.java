package crosstheborder.ui;

/**
 * @author Oscar
 * @autor Yannic
 *
 */

import crosstheborder.ui.controller.GameScreenController;
import crosstheborder.ui.controller.LayoutController;
import crosstheborder.ui.controller.LobbyMenuController;
import crosstheborder.ui.controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class of the application
 */
public class ClientMain extends Application {

    private Stage primaryStage;
    private BorderPane root;

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
        //Show login menu?
        showMainMenu();

    }

    /**
     * loads the layout into the stage
     */
    public void initLayout(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/Layout.fxml"));
            root = loader.load();
            LayoutController controller = loader.getController();
            controller.setInstance(this);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("could not load layout fxml");
            e.printStackTrace();
        }
    }

    /**
     * loads the main menu into the layout
     */
    public void showMainMenu(){
        Pane menuRoot;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/MainMenu.fxml"));
            menuRoot = loader.load();
            MainMenuController controller = loader.getController();
            controller.setInstance(this);
            root.setCenter(menuRoot);
            primaryStage.setTitle("main menu");
        }
        catch (IOException x){
            System.err.println("could not load main menu fxml");
            x.printStackTrace();
        }
    }

    /**
     * loads the lobby menu onto the layout
     */
    public void showLobbyMenu(){
        Pane lobbyRoot;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/LobbyMenu.fxml"));
            lobbyRoot = loader.load();
            LobbyMenuController controller = loader.getController();
            controller.setInstance(this);
            root.setCenter(lobbyRoot);
            primaryStage.setTitle("Lobby menu");
        }
        catch (IOException x){
            System.err.println("could not load lobby menu fxml");
            x.printStackTrace();
        }
    }

    public void showGameScreen(){
        //load the game fxml file.
        Canvas gameRoot;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/GameScreen.fxml"));
            gameRoot = loader.load();
            GameScreenController controller = loader.getController();
            controller.setInstance(this);
            root.setCenter(gameRoot);
            primaryStage.setTitle("in game");
        }
        catch (IOException x){
            System.err.println("could not load game screen fxml");
            x.printStackTrace();
        }
    }

    public int getMaxPlayers() {
        //temp method, kan miss vervangen worden door  getGame().getMaxPlayers ofzo
        return 8;
    }
}
