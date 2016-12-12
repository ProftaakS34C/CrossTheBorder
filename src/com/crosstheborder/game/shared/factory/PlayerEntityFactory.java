package com.crosstheborder.game.shared.factory;

import com.crosstheborder.game.shared.component.graphical.playergraphics.BorderPatrolGraphics;
import com.crosstheborder.game.shared.component.graphical.playergraphics.MexicanGraphics;
import com.crosstheborder.game.shared.component.physical.BorderPatrolPhysical;
import com.crosstheborder.game.shared.component.physical.MexicanPhysical;
import com.sstengine.player.playerentity.PlayerEntity;

/**
 * @author Oscar de Leeuw
 * @author guillaime
 */
public class PlayerEntityFactory {

    public PlayerEntity createMexican() {
        return new PlayerEntity(new MexicanPhysical(), new MexicanGraphics());
    }

    public PlayerEntity createBorderPatrol() {
        return new PlayerEntity(new BorderPatrolPhysical(), new BorderPatrolGraphics());
    }
}
