package crosstheborder.ui;

/**
 * Created by Oscar on 26-Sep-16.
 * Modified by Yannic on 03-Oct-16.
 */

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
 * Temp class for project registration in IntelliJ.
 */
public class ClientMain extends Application {

    private Stage primaryStage;
    private BorderPane root;
    private static ClientMain thisInstance;
    /**
     * not a Temp method.
     *
     * @param args penis
     */
    public static void main(String[] args) {

        launch(args);
    }

    /**
     * no Temp method no more.
     *
     * @param primaryStage penis
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        thisInstance = this;
        initLayout();
        //show login menu?
        showMainMenu();

    }

    public static ClientMain getInstance(){
        return thisInstance;
    }
    /**
     * loads the layout into the stage
     */
    public void initLayout(){
        try {
            root = FXMLLoader.load(getClass().getResource("Views/Layout.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.out.println("something went wrong loading the layout");
            e.printStackTrace();
        }
    }

    /**
     * loads the main menu into the layout
     */
    public void showMainMenu(){
        Pane menuRoot;
        try{
            menuRoot = FXMLLoader.load(getClass().getResource("Views/MainMenu.fxml"));
            root.setCenter(menuRoot);
            primaryStage.setTitle("main menu");
        }
        catch (IOException x){
            System.err.println("could not main menu fxml");
            x.printStackTrace();
        }
    }

    /**
     * loads the lobby menu onto the layout
     */
    public void showLobbyMenu(){
        Pane lobbyRoot;
        try{
            lobbyRoot = FXMLLoader.load(getClass().getResource("Views/LobbyMenu.fxml"));
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
        Canvas lobbyRoot;
        try{
            lobbyRoot = FXMLLoader.load(getClass().getResource("Views/GameScreen.fxml"));
            root.setCenter(lobbyRoot);
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
    //add stage loading methods here
    //zoals, loadLayout
    //loadMenu
    //loadLobbyMenu
    //loadGameScreen
}
