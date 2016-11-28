package com.crosstheborder.game.shared.component.graphical;

import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;

import java.awt.*;
import java.io.File;

/**
 * @author Oscar de Leeuw
 * @author guillaime
 */
public class MexicanGraphics extends GraphicsComponent {
    @Override
    public void render(Object caller, Painter painter, Point location, int width, int height) {
        painter.drawImage(new File("/images/mexican.png"), location, width, height);
    }
}
