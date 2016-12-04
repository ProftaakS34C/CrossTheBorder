package com.crosstheborder.game.shared.component.physical;

import com.crosstheborder.game.shared.strategy.accessibility.WallAccessibilityStrategy;
import com.crosstheborder.game.shared.strategy.interaction.WallInteractionStrategy;
import com.sstengine.component.physical.PhysicalComponent;
import com.sstengine.strategy.AccessibilityStrategy;
import com.sstengine.strategy.InteractionStrategy;

/**
 * @author guillaime
 */
public class WallPhysical extends PhysicalComponent {
    public WallPhysical() {
        super(new WallAccessibilityStrategy(), new WallInteractionStrategy());
    }
}
