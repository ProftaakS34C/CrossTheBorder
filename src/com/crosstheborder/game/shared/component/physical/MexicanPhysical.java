package com.crosstheborder.game.shared.component.physical;

import com.crosstheborder.game.shared.strategy.accessibility.MexicanAccessibiltyStrategy;
import com.crosstheborder.game.shared.strategy.interaction.MexicanInteractionStrategy;
import com.sstengine.component.physical.PhysicalComponent;
import com.sstengine.strategy.AccessibilityStrategy;
import com.sstengine.strategy.InteractionStrategy;

/**
 * @author Oscar de Leeuw
 * @author guillaime
 */
public class MexicanPhysical extends PhysicalComponent {
    public MexicanPhysical() {
        super(new MexicanAccessibiltyStrategy(), new MexicanInteractionStrategy());
    }
}
