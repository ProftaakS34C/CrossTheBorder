package com.crosstheborder.game.client;


import com.sstengine.component.graphics.Painter;
import javafx.scene.canvas.Canvas;
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
    private Canvas canvas;
    private FXFileToImageConverter imageConverter;


    public FXPainter(javafx.scene.canvas.Canvas canvas) {
        this.canvas = canvas;
        this.imageConverter = new FXFileToImageConverter();
    }

    @Override
    public int getHeight() {
        return (int) canvas.getHeight();
    }

    @Override
    public int getWidth() {
        return (int) canvas.getWidth();
    }

    @Override
    public void drawImage(File file, Point location, int width, int height) {
        Image img = imageConverter.getImage(file);
        canvas.getGraphicsContext2D().drawImage(img, location.x, location.y, width, height);
    }
}
