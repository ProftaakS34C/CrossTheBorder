package crosstheborder.lib.player.entity;

import crosstheborder.lib.Ability;
import crosstheborder.lib.player.PlayerEntity;

/**
 * This class represents the Mexican player object.
 * @author Joram
 * @version 1.0
 */
public class Mexican extends PlayerEntity {
    private boolean isTrapped;
    private Ability ability;

    /**
     * This is the constructor method of the class "Mexican".
     * in the constructor the name of the Mexican is set.
     * Calls the {@link PlayerEntity#PlayerEntity(String)} constructor.
     *
     * @param name    The name of the Mexican.
     * @param ability The ability of the Mexican.
     */
    public Mexican(String name, Ability ability) {
        super(name);
        this.ability = ability;
    }

    /**
     * This method is used to get isTrapped.
     *
     * @return The boolean the value of isTrapped.
     */
    public boolean getIsTrapped() {
        return isTrapped;
    }

    /**
     * Sets the value of the isTrapped field.
     *
     * @param value The new boolean value.
     */
    public void setIsTrapped(boolean value) {
        isTrapped = value;
    }

    /**
     * This method is used when a Mexican uses his ability.
     * the method {@link Ability#useAbility()} of {@link Ability} is called.
     */
    public void useAbility() {
        ability.useAbility();
    }

}
