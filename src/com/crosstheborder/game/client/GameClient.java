package com.crosstheborder.game.client;

import com.crosstheborder.game.client.painter.FXPainter;
import com.crosstheborder.game.shared.IGame;
import com.crosstheborder.game.shared.ui.EndScreen;
import com.crosstheborder.game.shared.ui.PlayerEntityUI;
import com.crosstheborder.game.shared.ui.TrumpUI;
import com.crosstheborder.game.shared.ui.UIExtension;
import com.sstengine.player.leader.Leader;
import com.sstengine.ui.KeyboardKey;
import com.sstengine.ui.UI;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Oscar de Leeuw
 */
public class GameClient extends Application {
    private static Logger LOGGER = Logger.getLogger(GameClient.class.getName());
    private static GameInterfacer gameInterfacer;

    private static String ipAddress;
    private static String publisherName;
    private static String playerName;

    private Canvas canvas;
    private UIExtension ui;

    private Timeline timeline;
    private List<KeyCode> activeKeys;

    public static void main(String[] args) {
        setup(args);

        launch(args);
    }

    public static void setup(String[] args){
        if (args.length > 2) {
            ipAddress = args[0];
            publisherName = args[1];
            playerName = args[2];
        }

    }

    @Override
    public void stop() throws Exception {
        try {
            gameInterfacer.unsubscribeListener();
            timeline.stop();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            System.exit(1);
        }

        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            StackPane root = new StackPane();
            Scene scene = new Scene(root, 1080d, 720d);

            canvas = new Canvas(1080d, 720d);
            root.getChildren().add(canvas);
            scene.setOnKeyPressed(this::handleKeyPress);
            scene.setOnKeyReleased(this::handleKeyRelease);

            activeKeys = new ArrayList<>();
            timeline = new Timeline(new KeyFrame(new Duration(1000 / 10), ae -> sendKeys()));
            timeline.setCycleCount(Animation.INDEFINITE);

            primaryStage.setScene(scene);
            primaryStage.show();
            gameInterfacer = new GameInterfacer(ipAddress, publisherName, this);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public void createUI(IGame game) {
        try {
            if (game.getPlayers().stream().filter(x -> x.getName().equals(playerName)).findFirst().get().getPlayable() instanceof Leader) {
                ui = new TrumpUI(new FXPainter(canvas), game, playerName);
            } else {
                ui = new PlayerEntityUI(new FXPainter(canvas), game, playerName);
            }
            timeline.play();
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public void endGame(/*IGame game*/){
        timeline.stop();
        //show the end screen here --- a screen with the final score, either victory or defeat and a continue button.
        System.out.println("show end-screen here");
        ui.showEndScreen();
    }

    public void render() {
        ui.render();
    }

    private void sendKeys() {
        activeKeys.forEach(x -> ui.sendKey(new KeyboardKey<KeyCode>(x)));
    }

    private void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();

        if (!activeKeys.contains(code)) {
            activeKeys.add(code);
        }
    }

    private void handleKeyRelease(KeyEvent event) {
        KeyCode code = event.getCode();
        activeKeys.removeIf(x -> x == code);
    }
}
