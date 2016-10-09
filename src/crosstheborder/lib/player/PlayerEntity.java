package crosstheborder.lib.player;

import crosstheborder.lib.InputBuffer;
import crosstheborder.lib.Player;
import crosstheborder.lib.Team;
import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.interfaces.TileObject;

import java.awt.*;
/**
 * Player entity is the super class for the Mexican and border patrol.
 *
 * @author Oscar de Leeuw
 */
public abstract class PlayerEntity extends Player implements TileObject {
    private Point location;
    private InputBuffer inputBuffer;
    private boolean canMove = true;
    //The amount of ticks till the player can move again.
    private int canMoveTicks;

    /**
     * Abstract constructor that passes the name to the Player class.
     * Calls the {@link Player#Player(String, Team)} constructor.
     *
     * @param name The name of the player.
     * @param team The team this player is part of.
     */
    public PlayerEntity(String name, Team team) {
        super(name, team);
        location = new Point();
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
     * Gets the next location this entity wants to move to.
     *
     * @return A point that represents the next location.
     */
    public Point getNextMove() {
        MoveDirection moveDirection = inputBuffer.getNextInputMove();

        //Exit the method as quickly as possible when there should be no movement.
        if (moveDirection == MoveDirection.NONE || !canMove) {
            return null;
        }

        Point currentLocation = getLocation();
        Point translation = moveDirection.getTranslation();
        return new Point(currentLocation.x + translation.x, currentLocation.y + translation.y);
    }

    /**
     * Pushes a MoveDirection to the {@link InputBuffer}.
     *
     * @param move The MoveDirection that should be pushed to the InputBuffer.
     */
    public void pushInput(MoveDirection move) {
        inputBuffer.addToInputMoves(move);
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
    public void immobilize(int seconds) {
        canMove = false;
        canMoveTicks = SERVER_TICK_RATE * seconds;
    }
}
