package com.crosstheborder.game.shared.util.enumeration;

import com.sstengine.map.tile.TileType;

/**
 * @author Oscar de Leeuw
 */
public enum CrossTheBorderTileType implements TileType<CrossTheBorderTileType> {
    DIRT('d'), GRASS('g'), SAND('s');

    private char code;

    private CrossTheBorderTileType(char c){
        this.code = c;
    }

    @Override
    public CrossTheBorderTileType getType() {
        return this;
    }

    public char getCode(){
        return code;
    }
}
