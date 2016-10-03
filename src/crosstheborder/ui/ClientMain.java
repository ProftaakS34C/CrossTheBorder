package crosstheborder.ui;

/**
 * Created by Oscar on 26-Sep-16.
 * Modified by Yannic on 03-Oct-16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Temp class for project registration in IntelliJ.
 */
public class ClientMain extends Application {

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
        Parent root;
        Scene scene;
        try{
            root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            scene = new Scene(root, 700, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Main");
            primaryStage.show();
        }
        catch (IOException x){
            System.out.println("could not load fxml");
            x.printStackTrace();
        }



    }
}
