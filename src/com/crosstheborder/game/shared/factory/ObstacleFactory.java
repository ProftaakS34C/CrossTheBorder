package com.crosstheborder.game.shared.factory;

import com.crosstheborder.game.shared.component.graphical.*;
import com.crosstheborder.game.shared.component.physical.WallPhysical;
import com.crosstheborder.game.shared.strategy.placement.TrapPlacementStrategy;
import com.crosstheborder.game.shared.strategy.placement.WallPlacementStrategy;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderPlaceableType;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderStaticObstacleType;
import com.sstengine.obstacle.Obstacle;
import com.sstengine.obstacle.placeableobstacle.PlaceableObstacle;
import com.sstengine.obstacle.staticobstacle.StaticObstacle;

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

    TreeGraphics treeGraphics = new TreeGraphics();
    WaterGraphics waterGraphics = new WaterGraphics();
    RockGraphics rockGraphics = new RockGraphics();

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

    public Obstacle createStaticObstacle(CrossTheBorderStaticObstacleType type){
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
