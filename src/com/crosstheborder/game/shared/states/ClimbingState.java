package com.crosstheborder.game.shared.states;

import com.sstengine.event.events.ChangePlayerEntityStateEvent;
import com.sstengine.event.framework.Event;
import com.sstengine.map.Map;
import com.sstengine.player.playerentity.MoveDirection;
import com.sstengine.player.playerentity.PlayerEntity;
import com.sstengine.player.playerentity.State;
import com.sstengine.player.playerentity.states.NormalState;

import java.util.List;

/**
 * Created by guill on 15-1-2017.
 */
public class ClimbingState implements State{

    int seconds;

    public ClimbingState(int seconds){
        this.seconds = seconds;
    }

    @Override
    public void handleInput(PlayerEntity playerEntity, MoveDirection moveDirection, Map map, List<Event> list) {
        if(--this.seconds <= 0){
            list.add(new ChangePlayerEntityStateEvent(playerEntity, new NormalState()));
        }
    }
}
