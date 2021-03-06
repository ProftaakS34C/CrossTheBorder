package com.crosstheborder.game.shared.strategy.accessibility;

import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderCountryTag;
import com.sstengine.component.physical.Physical;
import com.sstengine.player.playerentity.PlayerEntity;
import com.sstengine.strategy.AccessibilityStrategy;

/**
 * Created by guill on 29-11-2016.
 */
public class MexicoCountryAccessibilityStrategy implements AccessibilityStrategy {
    @Override
    public boolean execute(Physical physical, PlayerEntity playerEntity) {
        return playerEntity.getTeam().getCountry().getTag() == CrossTheBorderCountryTag.MEX;
    }
}
