package com.crosstheborder.game.shared.component.physical;

import com.crosstheborder.game.shared.strategy.accessibility.MexicanAccessibiltyStrategy;
import com.crosstheborder.game.shared.strategy.accessibility.MexicoCountryAccessibilityStrategy;
import com.crosstheborder.game.shared.strategy.interaction.MexicoCountryInteractionStrategy;
import com.sstengine.component.physical.PhysicalComponent;
import com.sstengine.strategy.AccessibilityStrategy;
import com.sstengine.strategy.InteractionStrategy;

/**
 * Created by guill on 29-11-2016.
 */
public class MexicoCountryPhysical extends PhysicalComponent {
    public MexicoCountryPhysical() {
        super(new MexicoCountryAccessibilityStrategy(), new MexicoCountryInteractionStrategy());
    }
}
