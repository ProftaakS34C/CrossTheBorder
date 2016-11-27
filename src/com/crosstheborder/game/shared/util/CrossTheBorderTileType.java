package com.crosstheborder.game.shared.util;

import com.sstengine.map.tile.TileType;

/**
 * @author Oscar de Leeuw
 */
public enum CrossTheBorderTileType implements TileType<CrossTheBorderTileType> {
    DIRT, GRASS, SAND;

    @Override
    public CrossTheBorderTileType getType() {
        return this;
    }
}
