package com.crosstheborder.game.shared.util;

import com.sstengine.obstacle.placeableobstacle.PlaceableType;

/**
 * Created by guill on 28-11-2016.
 */
public enum CrossTheBorderPlaceableType implements PlaceableType<CrossTheBorderPlaceableType>{
    WALL, TRAP;

    @Override
    public CrossTheBorderPlaceableType getType() {
        return this;
    }
}
