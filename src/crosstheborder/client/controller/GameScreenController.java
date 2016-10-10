package crosstheborder.client.controller;

import crosstheborder.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Yannic
 * The controller clas for the game screen
 */
public class GameScreenController {
    private GraphicsContext gc;

    @FXML
    private Canvas gameCanvas;
    @FXML
    private void initialize(){
        gc = gameCanvas.getGraphicsContext2D();
    }

    private ClientMain instance;

    /**
     * Sets the main class this controller uses for functions
     * @param instance the ClientMain class
     */
    public void setInstance(ClientMain instance) {
        this.instance = instance;
    }

    /**
     * Gets the graphicsContext of the gameCanvas used to draw on the canvas.
     * @return the graphicsContext of the gameCanvas.
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
