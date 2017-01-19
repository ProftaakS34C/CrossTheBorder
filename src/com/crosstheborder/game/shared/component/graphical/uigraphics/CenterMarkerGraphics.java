package com.crosstheborder.game.shared.component.graphical.uigraphics;

import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;

import java.awt.*;

/**
 * @author Oscar de Leeuw
 */
public class CenterMarkerGraphics extends GraphicsComponent {
    @Override
    public void render(Object o, Painter painter, Point point, int i, int i1) {
        painter.drawRectangle(point, i, i1, Color.RED, false);
    }
}
