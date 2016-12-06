package com.crosstheborder.game.shared.component.physical;

import com.crosstheborder.game.shared.strategy.accessibility.USACountryAccessibilityStrategy;
import com.crosstheborder.game.shared.strategy.interaction.USACountryInteractionStrategy;
import com.sstengine.component.physical.PhysicalComponent;
import com.sstengine.strategy.AccessibilityStrategy;
import com.sstengine.strategy.InteractionStrategy;

/**
 * Created by guill on 29-11-2016.
 */
public class USACountryPhysical extends PhysicalComponent {
    public USACountryPhysical() {
        super(new USACountryAccessibilityStrategy(), new USACountryInteractionStrategy());
    }
}
