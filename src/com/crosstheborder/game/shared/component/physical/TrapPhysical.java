package com.crosstheborder.game.shared.component.physical;

import com.crosstheborder.game.shared.strategy.accessibility.TrapAccessibilityStrategy;
import com.crosstheborder.game.shared.strategy.interaction.TrapInteractionStrategy;
import com.sstengine.component.physical.PhysicalComponent;
import com.sstengine.strategy.AccessibilityStrategy;
import com.sstengine.strategy.InteractionStrategy;

/**
 * @author guillaime
 */
public class TrapPhysical extends PhysicalComponent {
    protected TrapPhysical() {
        super(new TrapAccessibilityStrategy(), new TrapInteractionStrategy());
    }
}
