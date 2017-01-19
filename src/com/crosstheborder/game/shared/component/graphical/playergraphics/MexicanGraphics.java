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
public class MexicanGraphics extends GraphicsComponent {
    @Override
    public void render(Object caller, Painter painter, Point location, int width, int height) {
        PlayerEntity entity = (PlayerEntity) caller;

        painter.drawImage(ResourceLocator.getImage("mexican"), location, width, height);
        painter.drawString(entity.getPlayer().getName(), location, width, height - 20, Color.WHITE, true);
    }
}
