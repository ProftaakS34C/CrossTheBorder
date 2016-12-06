package com.crosstheborder.game.shared.strategy.interaction;

import com.crosstheborder.game.shared.util.CrossTheBorderCountryTag;
import com.sstengine.component.physical.Physical;
import com.sstengine.event.events.ChangePlayerEntityTileEvent;
import com.sstengine.event.events.ChangeTeamScoreEvent;
import com.sstengine.event.framework.Event;
import com.sstengine.player.playerentity.PlayerEntity;
import com.sstengine.strategy.InteractionStrategy;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author guillaime
 */
public class BorderPatrolInteractionStrategy implements InteractionStrategy {
    @Override
    public void execute(Physical physical, PlayerEntity playerEntity, List<Event> list) {

        PlayerEntity bp = (PlayerEntity) physical;

        if(playerEntity.getTeam().getCountry().getTag() == CrossTheBorderCountryTag.MEX){
            list.add(new ChangeTeamScoreEvent(bp.getTeam(), 1));
            list.add(new ChangePlayerEntityTileEvent(playerEntity, playerEntity.getTeam().getRespawnPoint(playerEntity, new Random())));
            list.add(new ChangePlayerEntityTileEvent(bp, bp.getTile()));
        }
    }
}
