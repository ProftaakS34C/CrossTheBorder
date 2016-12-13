package com.crosstheborder.game.shared.component.graphical.uigraphics;

import com.crosstheborder.game.shared.ui.uiobjects.PlaceableTracker;
import com.crosstheborder.game.shared.util.ResourceLocator;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderPlaceableType;
import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;
import com.sstengine.player.leader.PlaceableManager;

import java.awt.*;

/**
 * @author Oscar de Leeuw
 */
public class PlaceableTrackerGraphics extends GraphicsComponent {
    @Override
    public void render(Object o, Painter painter, Point point, int width, int height) {
        PlaceableTracker tracker = (PlaceableTracker) o;
        PlaceableManager manager = tracker.getManager();

        int y = point.y;
        int yStep = height / CrossTheBorderPlaceableType.values().length;

        //painter.drawRectangle(new Point(point.x, point.y), width, height, Color.red, true);

        for (CrossTheBorderPlaceableType type : CrossTheBorderPlaceableType.values()) {
            painter.drawImage(ResourceLocator.getImage(type.name().toLowerCase()), new Point(point.x, y), width, yStep);

            if (tracker.getActiveType() == type) {
                painter.drawRectangle(new Point(point.x, y), width, yStep, Color.BLUE, false);
            }

            painter.drawString("" + manager.getCounter(type).getAmount(), new Point(point.x + width / 3, y + yStep - 10), width, yStep, Color.WHITE, true);

            y += yStep;
        }
    }
}
