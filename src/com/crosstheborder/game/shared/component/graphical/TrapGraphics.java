package com.crosstheborder.game.shared.component.graphical;

import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;

import java.awt.*;
import java.io.File;

/**
 * @author guillaime
 */
public class TrapGraphics extends GraphicsComponent {
    @Override
    public void render(Object o, Painter painter, Point point, int width, int height) {
        painter.drawImage(new File("/images/trap.png"), point, width, height);
    }
}
