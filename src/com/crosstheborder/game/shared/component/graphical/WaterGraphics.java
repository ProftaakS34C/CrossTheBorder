package com.crosstheborder.game.shared.component.graphical;

import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;

import java.awt.*;
import java.io.File;

/**
 * Created by guill on 29-11-2016.
 */
public class WaterGraphics extends GraphicsComponent {
    @Override
    public void render(Object o, Painter painter, Point point, int width, int height) {
        painter.drawImage(new File("/images/water.png"), point, width, height);
    }
}
