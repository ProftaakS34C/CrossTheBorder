package crosstheborder.client.controller;

import crosstheborder.client.ClientMain;
import crosstheborder.client.FXPainter;
import crosstheborder.lib.Player;
import crosstheborder.lib.interfaces.Camera;
import crosstheborder.lib.interfaces.GameInterface;
import crosstheborder.lib.interfaces.Painter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

/**
 * The controller class for the game screen
 *
 * @author Yannic
 * @author Oscar de Leeuw
 */
public class GameScreenController {
    private final static int FRAMES_PER_SECOND = 60;
    private GraphicsContext gc;
    private Painter painter;
    private ClientMain main;

    private GameInterface game;
    private Player player;

    private Timeline timeline;

    @FXML
    private Canvas gameCanvas;

    @FXML
    private void initialize(){
        this.gc = gameCanvas.getGraphicsContext2D();
        this.painter = new FXPainter(gc);
    }

    /**
     * This method is used for first time setup of the controller, if the initialize method cannot be used.
     */
    public void setUp(ClientMain main) {
        this.game = main.getUser().getLobby().getGame();
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
        main.showLobbyMenu();
    }

    private void runGame() {
        draw();
    }

    private void draw() {
        Camera cam = game.getCamera(player.getCameraLocation(), 40, (int) gameCanvas.getWidth(), (int) gameCanvas.getHeight());
        System.out.println(timeline.getCurrentTime());
        cam.draw(painter);
    }
}
