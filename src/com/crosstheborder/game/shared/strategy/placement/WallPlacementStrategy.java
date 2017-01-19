package com.crosstheborder.game.shared.strategy.placement;

import com.sstengine.map.tile.Tile;
import com.sstengine.obstacle.placeableobstacle.PlaceableObstacle;
import com.sstengine.strategy.PlacementStrategy;
import com.sstengine.util.enumeration.OrdinalDirection;

import java.util.Map;

/**
 * @author Oscar de Leeuw
 * @author guillaime
 */
public class WallPlacementStrategy implements PlacementStrategy{
    @Override
    public boolean execute(PlaceableObstacle placeableObstacle, Map<OrdinalDirection, Tile> map, Tile tile) {
        return tile.getObstacle() == null && tile.getPlayerEntity() == null && tile.getCountry() == null;
    }
}
