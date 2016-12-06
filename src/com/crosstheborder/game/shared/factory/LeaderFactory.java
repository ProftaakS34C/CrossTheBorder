package com.crosstheborder.game.shared.factory;

import com.crosstheborder.game.shared.util.CrossTheBorderPlaceableType;
import com.sstengine.player.leader.Leader;
import com.sstengine.player.leader.PlaceableCounter;
import com.sstengine.player.leader.PlaceableManager;

/**
 * @author Oscar de Leeuw
 */
public class LeaderFactory {

    public Leader createTrump(){
        PlaceableManager manager = new PlaceableManager();
        manager.registerPlaceableType(CrossTheBorderPlaceableType.TRAP, new PlaceableCounter(5, 25));
        manager.registerPlaceableType(CrossTheBorderPlaceableType.WALL, new PlaceableCounter(10, 50));

        return new Leader(manager);
    }
}
