package com.crosstheborder.game.shared.component.graphical;

import com.crosstheborder.game.shared.util.ResourceLocator;
import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.io.File;

/**
 * @author guillaime
 */
public class WallGraphics extends GraphicsComponent {
    @Override
    public void render(Object o, Painter painter, Point point, int width, int height) {
        painter.drawImage(ResourceLocator.getImage("wall"), point, width, height);
    }
}