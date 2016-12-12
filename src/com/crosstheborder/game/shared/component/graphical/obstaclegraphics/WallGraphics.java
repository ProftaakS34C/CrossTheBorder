package com.crosstheborder.game.shared.component.graphical.obstaclegraphics;

import com.crosstheborder.game.shared.util.ResourceLocator;
import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;

import java.awt.*;

/**
 * @author guillaime
 */
public class WallGraphics extends GraphicsComponent {
    @Override
    public void render(Object o, Painter painter, Point point, int width, int height) {
        painter.drawImage(ResourceLocator.getImage("wall"), point, width, height);
    }
}
