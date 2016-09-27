package crosstheborder.lib.player.entity;

import crosstheborder.lib.player.PlayerEntity;

/**
 * This class represents the Border Patrol player object.
 * Extends the {@link PlayerEntity} class.
 * @author Joram
 * @version 1.0
 */
public class BorderPatrol extends PlayerEntity {

    /**
     * This is the constructor of the {@link BorderPatrol} class.
     * <p>
     * Calls the {@link PlayerEntity#PlayerEntity(String)} constructor.
     *
     * @param name The name of the player.
     */
    public BorderPatrol(String name) {
        super(name);
    }
}