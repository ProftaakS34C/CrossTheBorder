package com.crosstheborder.game.shared.component.graphical;

import com.crosstheborder.game.shared.util.CrossTheBorderTileType;
import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;
import com.sstengine.map.tile.Tile;

import java.awt.*;
import java.io.File;

/**
 * @author guillaime
 */
public class TileGraphics extends GraphicsComponent {
    @Override
    public void render(Object o, Painter painter, Point point, int i, int i1) {

        Tile caller = (Tile)o;
        CrossTheBorderTileType tileType = (CrossTheBorderTileType)caller.getType();

        switch (tileType){
            case DIRT:
                painter.drawImage(new File("/images/dirt.png"), point, i , i1);
                break;
            case SAND:
                painter.drawImage(new File("/images/sand.png"), point, i , i1);
                break;
            case GRASS:
                painter.drawImage(new File("/images/grass.png"), point, i , i1);
                break;
        }
    }
}
