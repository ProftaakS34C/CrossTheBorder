package com.crosstheborder.game.shared.component.physical;

import com.crosstheborder.game.shared.strategy.accessibility.TrapAccessibilityStrategy;
import com.crosstheborder.game.shared.strategy.interaction.TrapInteractionStrategy;
import com.sstengine.component.physical.PhysicalComponent;

/**
 * @author guillaime
 */
public class TrapPhysical extends PhysicalComponent {
    public TrapPhysical() {
        super(new TrapAccessibilityStrategy(), new TrapInteractionStrategy());
    }
}
