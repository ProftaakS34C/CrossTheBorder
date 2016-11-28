package com.crosstheborder.game.shared.factory;

import com.crosstheborder.game.shared.component.graphical.TrapGraphics;
import com.crosstheborder.game.shared.component.graphical.WallGraphics;
import com.crosstheborder.game.shared.component.physical.WallPhysical;
import com.crosstheborder.game.shared.strategy.placement.TrapPlacementStrategy;
import com.crosstheborder.game.shared.strategy.placement.WallPlacementStrategy;
import com.crosstheborder.game.shared.util.CrossTheBorderPlaceableType;
import com.sstengine.obstacle.Obstacle;
import com.sstengine.obstacle.placeableobstacle.PlaceableObstacle;

/**
 * @author Oscar de Leeuw
 * @author guillaime
 */
public class ObstacleFactory {
    WallPhysical wallPhysical = new WallPhysical();

    WallPlacementStrategy wall = new WallPlacementStrategy();
    WallGraphics wallGraphics = new WallGraphics();

    TrapPlacementStrategy trap = new TrapPlacementStrategy();
    TrapGraphics trapGraphics = new TrapGraphics();

    int count = 0;

    public Obstacle createPlaceableObstacle(CrossTheBorderPlaceableType type) {

        count++;

        switch (type){
            case WALL:
                return new PlaceableObstacle(count, wallPhysical, wallGraphics, wall, type);
            case TRAP:
                return new PlaceableObstacle(count, wallPhysical, trapGraphics, trap, type);
        }
        return null;
    }
}
