package com.crosstheborder.game.shared.factory;

import com.crosstheborder.game.shared.component.graphical.BorderPatrolGraphics;
import com.crosstheborder.game.shared.component.graphical.MexicanGraphics;
import com.crosstheborder.game.shared.component.physical.BorderPatrolPhysical;
import com.crosstheborder.game.shared.component.physical.MexicanPhysical;
import com.sstengine.player.playerentity.PlayerEntity;

/**
 * @author Oscar de Leeuw
 * @author guillaime
 */
public class PlayerEntityFactory {

    public PlayerEntity createMexican(int id) {
        return new PlayerEntity(id, new MexicanPhysical(), new MexicanGraphics());
    }

    public PlayerEntity createBorderPatrol(int id) {
        return new PlayerEntity(id, new BorderPatrolPhysical(), new BorderPatrolGraphics());
    }
}
