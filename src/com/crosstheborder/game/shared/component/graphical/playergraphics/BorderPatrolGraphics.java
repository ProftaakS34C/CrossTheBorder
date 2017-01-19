package com.crosstheborder.game.shared.component.graphical.playergraphics;

import com.crosstheborder.game.shared.util.ResourceLocator;
import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;
import com.sstengine.player.playerentity.PlayerEntity;

import java.awt.*;

/**
 * @author Oscar de Leeuw
 * @author guillaime
 */
public class BorderPatrolGraphics extends GraphicsComponent {
    @Override
    public void render(Object o, Painter painter, Point point, int width, int height) {
        PlayerEntity entity = (PlayerEntity) o;

        painter.drawImage(ResourceLocator.getImage("borderpatrol"), point, width, height);
        painter.drawString(entity.getPlayer().getName(), point, width, height - 20, Color.WHITE, true);
    }
}
