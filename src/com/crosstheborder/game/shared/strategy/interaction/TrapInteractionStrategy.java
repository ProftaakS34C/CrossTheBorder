package com.crosstheborder.game.shared.strategy.interaction;

import com.crosstheborder.game.shared.util.CrossTheBorderCountryTag;
import com.sstengine.component.physical.Physical;
import com.sstengine.event.events.ChangeObstacleTileEvent;
import com.sstengine.event.events.ChangePlayerEntityStateEvent;
import com.sstengine.event.framework.Event;
import com.sstengine.obstacle.Obstacle;
import com.sstengine.player.playerentity.PlayerEntity;
import com.sstengine.player.playerentity.states.ImmobilizedState;
import com.sstengine.strategy.InteractionStrategy;

import java.util.List;

/**
 * @author guillaime
 */
public class TrapInteractionStrategy implements InteractionStrategy {
    @Override
    public void execute(Physical physical, PlayerEntity playerEntity, List<Event> list) {

        Obstacle trap = (Obstacle) physical;

        if(playerEntity.getTeam().getCountry().getTag() == CrossTheBorderCountryTag.MEX){
            list.add(new ChangePlayerEntityStateEvent(playerEntity, new ImmobilizedState(15)));
            list.add(new ChangeObstacleTileEvent(trap, null));
        }
    }
}
