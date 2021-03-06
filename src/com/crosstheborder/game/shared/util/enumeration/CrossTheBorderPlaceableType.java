package com.crosstheborder.game.shared.util.enumeration;

import com.sstengine.obstacle.placeableobstacle.PlaceableType;

/**
 * @author guillaime
 */
public enum CrossTheBorderPlaceableType implements PlaceableType<CrossTheBorderPlaceableType>{
    WALL, TRAP;

    @Override
    public CrossTheBorderPlaceableType getType() {
        return this;
    }
}
