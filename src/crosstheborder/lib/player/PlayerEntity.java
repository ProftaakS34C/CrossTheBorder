package crosstheborder.lib.player;

import crosstheborder.lib.ImageFinder;
import crosstheborder.lib.InputBuffer;
import crosstheborder.lib.Player;
import crosstheborder.lib.Team;
import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.interfaces.*;

import java.awt.*;
/**
 * Player entity is the super class for the Mexican and border patrol.
 *
 * @author Oscar de Leeuw
 */
public abstract class PlayerEntity extends Player implements Drawable, Interactable {
    private Point location;
    private InputBuffer inputBuffer;
    private boolean canMove = true;
    //The amount of ticks till the player can move again.
    private int canMoveTicks;

    /**
     * Abstract constructor that passes the name to the Player class.
     * Calls the {@link Player#Player(String, Team, GameSettings)} constructor.
     *
     * @param name The name of the player.
     * @param team The team this player is part of.
     * @param settings The settings of the game.
     */
    public PlayerEntity(String name, Team team, GameSettings settings) {
        super(name, team, settings);
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
        canMoveTicks = serverTickRate * seconds;
    }

    /**
     * Method for handling the interaction between two PlayerEntities.
     * Calls methods on the {@link GameManipulator} object to process interaction results.
     *
     * @param player The other PlayerEntity that is interacting with this playerEntity.
     * @param game   A {@link GameManipulator} on which interaction results can be executed.
     * @return A boolean representing whether further movement/interaction should be evaluated.
     */
    public abstract boolean interactWith(PlayerEntity player, GameManipulator game);

    /**
     * {@inheritDoc}
     * Returns a clone of the location of the object.
     */
    @Override
    public Point getCameraLocation() {
        return (Point) this.location.clone();
    }

    /**
     * {@inheritDoc}
     * Is intentionally left blank. Camera movement for player entities is not supported.
     */
    @Override
    public void moveCameraLocation(MoveDirection md) {
        //Intentionally left blank. Cannot support moving the camera due to the way player location is updated.
    }

    @Override
    public void draw(Painter painter, Point location, int tileWidth) {
        painter.drawImage(ImageFinder.getInstance().getImage(this), location, tileWidth, tileWidth);
    }
}
