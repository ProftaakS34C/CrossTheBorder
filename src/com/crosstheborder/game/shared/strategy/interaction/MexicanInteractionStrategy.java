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

/**
 * @author Oscar de Leeuw
 * @author guillaime
 */
public class MexicanInteractionStrategy implements InteractionStrategy {
    @Override
    public void execute(Physical physical, PlayerEntity playerEntity, List<Event> list) {

        PlayerEntity mexican = (PlayerEntity) physical;

        if(playerEntity.getTeam().getCountry().getTag() == CrossTheBorderCountryTag.USA){
            list.add(new ChangeTeamScoreEvent(playerEntity.getTeam(), 1));
            list.add(new ChangePlayerEntityTileEvent(mexican, mexican.getTeam().getRespawnPoint(mexican, new Random())));
        }
    }
}
