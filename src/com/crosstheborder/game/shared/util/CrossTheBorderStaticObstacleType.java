package com.crosstheborder.game.shared.util;

import com.sstengine.obstacle.staticobstacle.StaticObstacleType;

/**
 * Created by guill on 29-11-2016.
 */
public enum CrossTheBorderStaticObstacleType implements StaticObstacleType<CrossTheBorderStaticObstacleType> {
    TREE('t'), ROCK('r'), WATER('w');

    private char code;

    private CrossTheBorderStaticObstacleType(char c){
        this.code = c;
    }

    @Override
    public CrossTheBorderStaticObstacleType getType() {
        return this;
    }

    public char getCode(){
        return code;
    }
}
