package crosstheborder.lib.ability;

import crosstheborder.lib.Ability;

/**
 * Created by Oscar on 26-Sep-16.
 */
public class Crawler extends Ability {
    /**
     * This is the constructor method of the class "Ability"
     * in the constructor the cooldownTime is set and readyToUse is set to true;
     *
     * @param cooldownTime the time it takes for the ability to be available again
     */
    public Crawler(int cooldownTime) {
        super(cooldownTime);
    }

    /**
     * This method is used when a player activates an ability
     * readyToUse is set to false
     *
     * @return boolean returns true if the ability was activated, if not this method returns false
     */
    @Override
    public boolean useAbility() {
        throw new UnsupportedOperationException();
    }
}
