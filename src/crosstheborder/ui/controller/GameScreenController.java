package crosstheborder.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Yannic
 * this is the controller for the GameScreen fxml,
 * which is used for displaying the game to the users
 */
public class GameScreenController {
    GraphicsContext gc;

    @FXML
    private Canvas gameCanvas;
    @FXML
    private void initialize(){
        gc = gameCanvas.getGraphicsContext2D();
    }


    /**
     *
     * @return the graphicsContext of the gameCanvas used to draw on the canvas.
     */
    public GraphicsContext getGc() {
        return gc;
    }

    /**
     * tbt
     */
    public void draw(){
        //draw the game using a Paintable interface similar to JCC.
    }
}
