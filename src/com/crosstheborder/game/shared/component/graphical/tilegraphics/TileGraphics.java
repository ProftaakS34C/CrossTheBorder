package com.crosstheborder.game.shared.component.graphical.tilegraphics;

import com.crosstheborder.game.shared.util.ResourceLocator;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderTileType;
import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;
import com.sstengine.map.tile.Tile;

import java.awt.*;

/**
 * @author guillaime
 */
public class TileGraphics extends GraphicsComponent {
    @Override
    public void render(Object o, Painter painter, Point point, int width, int height) {

        Tile caller = (Tile)o;
        CrossTheBorderTileType tileType = (CrossTheBorderTileType)caller.getType();

        switch (tileType){
            case DIRT:
                painter.drawImage(ResourceLocator.getImage("dirt"), point, width , height);
                break;
            case SAND:
                painter.drawImage(ResourceLocator.getImage("sand"), point, width , height);
                break;
            case GRASS:
                painter.drawImage(ResourceLocator.getImage("grass"), point, width , height);
                break;
        }
    }
}
