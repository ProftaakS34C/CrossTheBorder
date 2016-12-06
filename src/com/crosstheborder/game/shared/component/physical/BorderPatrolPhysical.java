package com.crosstheborder.game.shared.component.physical;

import com.crosstheborder.game.shared.strategy.accessibility.BorderPatrolAccessibilityStrategy;
import com.crosstheborder.game.shared.strategy.interaction.BorderPatrolInteractionStrategy;
import com.sstengine.component.physical.PhysicalComponent;
import com.sstengine.strategy.AccessibilityStrategy;
import com.sstengine.strategy.InteractionStrategy;

/**
 * @author Oscar de Leeuw
 * @author guillaime
 */
public class BorderPatrolPhysical extends PhysicalComponent {
    public BorderPatrolPhysical() {
        super(new BorderPatrolAccessibilityStrategy(), new BorderPatrolInteractionStrategy());
    }
}
