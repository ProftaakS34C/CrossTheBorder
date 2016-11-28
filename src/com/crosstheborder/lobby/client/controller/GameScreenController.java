package com.crosstheborder.lobby.client.controller;

import com.crosstheborder.lobby.client.ClientMain;
import com.crosstheborder.lobby.client.FXPainter;
import com.crosstheborder.lobby.client.InputConverter;
import crosstheborder.lib.Player;
import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.interfaces.Camera;
import crosstheborder.lib.interfaces.GameInterface;
import crosstheborder.lib.interfaces.Painter;
import crosstheborder.lib.player.PlayerEntity;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller class for the game screen
 *
 * @author Yannic
 * @author Oscar de Leeuw
 */
public class GameScreenController {
    private final static int FRAMES_PER_SECOND = 30;
    private GraphicsContext gc;
    private Painter painter;
    private ClientMain main;
    private InputConverter inputConverter;
    private List<KeyCode> activeKeys;

    private GameInterface game;
    private Player player;

    private Timeline timeline;

    @FXML
    private Canvas gameCanvas;

    @FXML
    private void initialize(){
        this.gc = gameCanvas.getGraphicsContext2D();
        this.painter = new FXPainter(gc);
        this.inputConverter = new InputConverter();
        this.activeKeys = new ArrayList<>();
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();

        if (!activeKeys.contains(code)) {
            activeKeys.add(code);
        }
    }

    @FXML
    void handleKeyRelease(KeyEvent event) {
        KeyCode code = event.getCode();
        activeKeys.removeIf(x -> x == code);
    }

    private void sendMoves() {
        if (!activeKeys.isEmpty()) {
            MoveDirection move = inputConverter.getMoveDirectionFromKey(activeKeys.get(activeKeys.size() - 1));
            game.sendMoveInput(move, (PlayerEntity) player);
        }
    }

    /**
     * This method is used for first time setup of the controller, if the initialize method cannot be used.
     */
    public void setUp(ClientMain main) {
        //this.game = main.getUser().getRoom().getGame();
        this.player = main.getUser().getPlayer();
        start();
    }

    private void start() {
        timeline = new Timeline(new KeyFrame(new Duration(1000 / FRAMES_PER_SECOND), ae -> runGame()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void stop() {
        timeline.stop();
        main.showRoomMenu();
    }

    private void runGame() {
        sendMoves(); //TODO Should be reque.stable by the server every time it wants to calculate a move.
        draw();
    }

    private void draw() {
        Camera cam = game.getCamera(player.getCameraLocation(), 40, (int) gameCanvas.getWidth(), (int) gameCanvas.getHeight());
        cam.draw(painter);
    }
}
