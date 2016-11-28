package com.crosstheborder.game.shared.strategy.accessibility;

import com.sstengine.component.physical.Physical;
import com.sstengine.player.playerentity.PlayerEntity;
import com.sstengine.strategy.AccessibilityStrategy;

/**
 * @author guillaime
 */
public class WallAccessibilityStrategy implements AccessibilityStrategy {
    @Override
    public boolean execute(Physical physical, PlayerEntity playerEntity) {
        return physical.isAccessible(playerEntity);
    }
}
