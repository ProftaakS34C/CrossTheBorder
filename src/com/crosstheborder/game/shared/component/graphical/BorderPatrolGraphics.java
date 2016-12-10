package com.crosstheborder.game.shared.component.graphical;

import com.crosstheborder.game.shared.util.ResourceLocator;
import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.player.entity.BorderPatrol;

import java.awt.*;
import java.io.File;

/**
 * @author Oscar de Leeuw
 * @author guillaime
 */
public class BorderPatrolGraphics extends GraphicsComponent {
    @Override
    public void render(Object o, Painter painter, Point point, int width, int height) {
        painter.drawImage(ResourceLocator.getImage("borderpatrol"), point, width, height);
    }
}