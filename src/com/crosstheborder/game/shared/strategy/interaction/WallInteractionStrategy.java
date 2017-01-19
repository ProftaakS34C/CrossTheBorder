package com.crosstheborder.game.shared.strategy.interaction;

import com.crosstheborder.game.shared.states.ClimbingState;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderCountryTag;
import com.sstengine.component.physical.Physical;
import com.sstengine.event.events.ChangePlayerEntityStateEvent;
import com.sstengine.event.framework.Event;
import com.sstengine.player.playerentity.PlayerEntity;
import com.sstengine.strategy.InteractionStrategy;

import java.util.List;

/**
 *@author guillaime
 */
public class WallInteractionStrategy implements InteractionStrategy {
    @Override
    public void execute(Physical physical, PlayerEntity playerEntity, List<Event> list) {

        if(playerEntity.getTeam().getCountry().getTag() == CrossTheBorderCountryTag.MEX){
            list.add(new ChangePlayerEntityStateEvent(playerEntity, new ClimbingState(20)));
        }
    }
}
