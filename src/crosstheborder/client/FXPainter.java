package crosstheborder.client;

import crosstheborder.lib.interfaces.Painter;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.File;

/**
 * This is the javaFX implementation of the Painter interface
 * @author yannic
 */
public class FXPainter implements Painter {
    private GraphicsContext gc;

    public FXPainter(GraphicsContext gc){
        this.gc = gc;
    }
    @Override
    public void drawImage(File file, Point location, int width, int height) {
        javafx.scene.image.Image img = new Image(file.getPath());
        gc.drawImage(img, location.x, location.y, width, height);
    }
}
