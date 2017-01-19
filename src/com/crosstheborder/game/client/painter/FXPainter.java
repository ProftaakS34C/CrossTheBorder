package com.crosstheborder.game.client.painter;


import com.sstengine.component.graphics.Painter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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

    @Override
    public void drawRectangle(Point point, int width, int height, java.awt.Color color, boolean fill) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Color fxColor = FXColorConverter.getColor(color);

        if (fill) {
            gc.setFill(fxColor);
            gc.fillRect(point.x, point.y, width, height);
        } else {
            gc.setStroke(fxColor);
            gc.strokeRect(point.x, point.y, width, height);
        }
    }

    @Override
    public void drawString(String text, Point point, int width, int height, java.awt.Color color, boolean fill) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Color fxColor = FXColorConverter.getColor(color);
        Text texty = new Text(text);

        gc.setFont(new Font(height * 72 / 96));

        if (fill) {
            gc.setFill(fxColor);
            gc.fillText(text, point.x, point.y, width);
        } else {
            gc.setStroke(fxColor);
            gc.strokeText(text, point.x, point.y, width);
        }
    }

    @Override
    public void drawEllipse(Point point, int width, int height, java.awt.Color color, boolean fill) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Color fxColor = FXColorConverter.getColor(color);

        if (fill) {
            gc.setFill(fxColor);
            gc.fillOval(point.x, point.y, width, height);
        } else {
            gc.setStroke(fxColor);
            gc.fillOval(point.x, point.y, width, height);
        }
    }
}
