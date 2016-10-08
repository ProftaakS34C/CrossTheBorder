package crosstheborder.lib.player;

import crosstheborder.lib.Player;
import crosstheborder.lib.Team;

/**
 *  Represents the Trump player object.
 *  Extends the {@link Player} class.
 *  Holds the amount of walls and traps the Trump has.
 */
public class Trump extends Player {

    private int wallAmount;
    private int trapAmount;

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
     * Gets the amount of walls.
     *
     * @return The amount of walls Trump currently has.
     */
    public int getWallAmount() {
        return wallAmount;
    }

    /**
     * Increases the amount of walls by 1.
     */
    public void increaseWallAmount() {
        this.wallAmount++;
    }

    /**
     * Decreases the amount of walls by 1.
     */
    public void decreaseWallAmount() {
        this.wallAmount--;
    }

    /**
     * Gets the amount of traps.
     *
     * @return The amount of traps Trump currently has.
     */
    public int getTrapAmount() {
        return trapAmount;
    }

    /**
     * Increases the amount of traps by 1.
     */
    public void increaseTrapAmount() {
        this.trapAmount++;
    }

    /**
     * Decreases the amount of traps by 1.
     */
    public void decreaseTrapAmount() {
        this.trapAmount--;
    }
}
