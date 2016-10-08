package crosstheborder.lib.player;

import crosstheborder.lib.InputBuffer;
import crosstheborder.lib.Player;
import crosstheborder.lib.Team;
import crosstheborder.lib.interfaces.TileObject;

import java.awt.*;
/**
 * Player entity is the super class for the Mexican and border patrol.
 *
 * @author Oscar de Leeuw
 */
public abstract class PlayerEntity extends Player implements TileObject {
    protected Point location;
    protected boolean isPassable;
    private InputBuffer inputBuffer;
    private boolean canMove = true;
    //The amount of ticks till the player can move again.
    private int canMoveTicks;

    /**
     * Abstract constructor that passes the name to the Player class.
     * Calls the {@link Player#Player(String, Team)} constructor.
     *
     * @param name The name of the player.
     * @param location The location of the player.
     * @param team The team this player is part of.
     */
    public PlayerEntity(String name, Point location, Team team) {
        super(name, team);
        this.location = location;
        this.inputBuffer = new InputBuffer();
    }

    /**
     * Gets the location of the {@link TileObject}.
     *
     * @return A point that represents the location of the {@link TileObject}.
     */
    public Point getLocation() {
        return this.location;
    }

    /**
     * Gets the inputbuffer of the entity.
     * @return The inputbuffer of the player entity.
     */
    public InputBuffer getInputBuffer() {
        return this.inputBuffer;
    }

    /**
     * Gets whether the player can move or not.
     *
     * @return A boolean that represents whether the player can move.
     */
    public boolean getCanMove() {
        return this.canMove;
    }

    /**
     * Decreases the timer on the movement timer if the entity can't move.
     */
    public void decreaseMoveTimer() {
        //If the player can't move decrease the move timer.
        if (!canMove) {
            //Decrease by 1 tick
            canMoveTicks--;

            if (canMoveTicks <= 0) {
                canMove = true;
                canMoveTicks = 0;
            }
        }
    }

    /**
     * Immobilizes the player and sets the timer for when the player can move again.
     *
     * @param seconds The amount of seconds the player is immobile.
     */
    public void setCanMoveTicks(int seconds) {
        canMove = false;
        canMoveTicks = SERVER_TICK_RATE * seconds;
    }
}
