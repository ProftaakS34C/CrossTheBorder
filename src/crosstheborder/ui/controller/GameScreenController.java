package crosstheborder.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by yannic on 04/10/2016.
 */
public class GameScreenController {
    GraphicsContext gc;

    @FXML
    private void initialize(){
        gc = gameCanvas.getGraphicsContext2D();
    }
    @FXML
    private Canvas gameCanvas;

    public Canvas getGameCanvas() {
        return gameCanvas;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void draw(){
        //bla bla bla teken game ofzo
    }
}
