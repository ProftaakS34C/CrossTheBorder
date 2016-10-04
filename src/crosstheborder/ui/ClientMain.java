package crosstheborder.ui;

/**
 * Created by Oscar on 26-Sep-16.
 * Modified by Yannic on 03-Oct-16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        initLayout();

        showMainMenu();

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
        //lobby menu does not exist yet.
        Pane lobbyRoot;
        Scene scene;
        try{
            lobbyRoot = FXMLLoader.load(getClass().getResource("LobbyMenu.fxml"));
            root.setCenter(lobbyRoot);
            primaryStage.setTitle("lobby menu");
        }
        catch (IOException x){
            System.err.println("could not load lobby menu fxml");
            x.printStackTrace();
        }
    }
    //add stage loading methods here
    //zoals, loadLayout
    //loadMenu
    //loadLobbyMenu
    //loadGameScreen
}
