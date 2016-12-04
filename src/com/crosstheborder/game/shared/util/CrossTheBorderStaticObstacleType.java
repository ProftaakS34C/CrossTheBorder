package com.crosstheborder.game.shared.util;

import com.sstengine.obstacle.staticobstacle.StaticObstacleType;

/**
 * Created by guill on 29-11-2016.
 */
public enum CrossTheBorderStaticObstacleType implements StaticObstacleType<CrossTheBorderStaticObstacleType> {
    TREE, ROCK, WATER;

    @Override
    public CrossTheBorderStaticObstacleType getType() {
        return this;
    }
}
