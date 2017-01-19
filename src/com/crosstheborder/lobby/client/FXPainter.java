package com.crosstheborder.lobby.client;

import crosstheborder.lib.interfaces.Painter;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.File;

/**
 * This is the javaFX implementation of the Painter interface
 *
 * @author yannic
 * @author Oscar de Leeuw
 */
public class FXPainter implements Painter {
    private GraphicsContext gc;
    private FXFileToImageConverter imageConverter;


    public FXPainter(GraphicsContext gc) {
        this.gc = gc;
        this.imageConverter = new FXFileToImageConverter();

    }

    @Override
    public void drawImage(File file, Point location, int width, int height) {
        Image img = imageConverter.getImage(file);
        gc.drawImage(img, location.x, location.y, width, height);
    }
}
