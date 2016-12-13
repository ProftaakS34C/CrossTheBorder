package com.crosstheborder.game.shared.factory;

import com.crosstheborder.game.shared.component.graphical.obstaclegraphics.*;
import com.crosstheborder.game.shared.component.physical.TrapPhysical;
import com.crosstheborder.game.shared.component.physical.WallPhysical;
import com.crosstheborder.game.shared.strategy.placement.TrapPlacementStrategy;
import com.crosstheborder.game.shared.strategy.placement.WallPlacementStrategy;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderPlaceableType;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderStaticObstacleType;
import com.sstengine.obstacle.placeableobstacle.PlaceableObstacle;
import com.sstengine.obstacle.staticobstacle.StaticObstacle;

/**
 * @author Oscar de Leeuw
 * @author guillaime
 */
public class ObstacleFactory {
    WallPhysical wallPhysical = new WallPhysical();
    TrapPhysical trapPhysical = new TrapPhysical();

    WallPlacementStrategy wall = new WallPlacementStrategy();
    WallGraphics wallGraphics = new WallGraphics();

    TrapPlacementStrategy trap = new TrapPlacementStrategy();
    TrapGraphics trapGraphics = new TrapGraphics();

    TreeGraphics treeGraphics = new TreeGraphics();
    WaterGraphics waterGraphics = new WaterGraphics();
    RockGraphics rockGraphics = new RockGraphics();

    int count = 0;

    public PlaceableObstacle createPlaceableObstacle(CrossTheBorderPlaceableType type) {
        count++;

        switch (type){
            case WALL:
                return new PlaceableObstacle(count, wallPhysical, wallGraphics, wall, type);
            case TRAP:
                return new PlaceableObstacle(count, trapPhysical, trapGraphics, trap, type);
        }
        return null;
    }

    public StaticObstacle createStaticObstacle(CrossTheBorderStaticObstacleType type) {
        count++;

        switch (type){
            case TREE:
                return new StaticObstacle(count, treeGraphics, type);
            case WATER:
                return new StaticObstacle(count, waterGraphics, type);
            case ROCK:
                return new StaticObstacle(count, rockGraphics, type);
        }
        return null;
    }
}
