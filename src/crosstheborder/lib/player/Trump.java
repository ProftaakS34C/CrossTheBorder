package crosstheborder.lib.player;

import crosstheborder.lib.Player;
import crosstheborder.lib.Team;
import crosstheborder.lib.tileobject.Placeable;
import crosstheborder.lib.tileobject.placeable.Trap;
import crosstheborder.lib.tileobject.placeable.Wall;

/**
 *  Represents the Trump player object.
 *  Extends the {@link Player} class.
 *  Holds the amount of walls and traps the Trump has.
 *
 *  @author Oscar de Leeuw
 */
public class Trump extends Player {
    //Determines how often Trump gets a wall in seconds.
    private final int SECONDS_PER_WALL = 3; //TODO gather this from game settings.
    //Determines how often Trump gets a wall in seconds.
    private final int SECONDS_PER_TRAP = 4; //TODO gather this from game settings.

    private int wallAmount;
    private int trapAmount;

    private int wallTickTimer;
    private int trapTickTimer;

    /**
     * Creates a new Trump object with the given name.
     * Calls the {@link Player#Player(String, Team)} constructor.
     *
     * @param name The name of the player.
     * @param team The team this player is part of.
     */
    public Trump(String name, Team team) {
        super(name, team);
    }

    /**
     * Gets the amount of traps Trump can place.
     * @return An integer that represents the amount of traps Trump can place.
     */
    public int getTrapAmount() {
        return this.trapAmount;
    }

    /**
     * Gets the amount of walls Trump can place.
     *
     * @return An integer that represents the amount of walls Trump can place.
     */
    public int getWallAmount() {
        return this.wallAmount;
    }

    /**
     * Determines whether a given placeable can be placed by Trump.
     *
     * @param placeable The placeable that could be placed.
     * @return A boolean indicating whether Trump is allowed to place the placeable.
     */
    public boolean canPlace(Placeable placeable) {
        return (placeable instanceof Wall && wallAmount > 0) || (placeable instanceof Trap && trapAmount > 0);
    }

    /**
     * Decreases the amount of placeables Trump can place of the given placeable.
     * @param placeable The placeable Trump has placed.
     */
    public void decreasePlaceableAmount(Placeable placeable) {
        if (placeable instanceof Wall) {
            wallAmount--;
        } else if (placeable instanceof Trap) {
            trapAmount--;
        }
    }

    /**
     * Increases the tick timer for Trump's placeables.
     * When a certain threshold has been met it increases the amount of placeables Trump can place.
     */
    public void tickPlaceableAmount() {
        wallTickTimer++;
        trapTickTimer++;

        if (wallTickTimer >= SERVER_TICK_RATE * SECONDS_PER_WALL) {
            wallTickTimer = 0;
            wallAmount++;
        }

        if (trapTickTimer >= SERVER_TICK_RATE * SECONDS_PER_TRAP) {
            trapTickTimer = 0;
            trapAmount++;
        }
    }
}
