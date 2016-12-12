package com.crosstheborder.game.shared.factory;

import com.crosstheborder.game.shared.component.graphical.tilegraphics.TileGraphics;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderTileType;
import com.sstengine.map.tile.Tile;

import java.awt.*;

/**
 * @author Oscar de Leeuw
 */
public class TileFactory {

    TileGraphics tileGraphics = new TileGraphics();

    public Tile createTile(int id, CrossTheBorderTileType type, Point location) {
        return new Tile(id, tileGraphics, type, location);
    }
}
