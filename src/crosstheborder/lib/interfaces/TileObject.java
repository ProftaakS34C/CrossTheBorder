package crosstheborder.lib.interfaces;

import crosstheborder.lib.player.PlayerEntity;

import java.awt.*;

/**
 * The TileObject class is an interface for objects that can live on {@link crosstheborder.lib.Tile}s within the game.
 *
 * @author Oscar de Leeuw
 */
public interface TileObject {
    /**
     * Method for handling the interaction between a {@link PlayerEntity} and a {@link TileObject}.
     * Calls methods on the {@link GameManipulator} object to process interaction results.
     *
     * @param player The {@link PlayerEntity} that is interacting with the TileObject.
     * @param game A {@link GameManipulator} on which interaction results can be executed.
     */
    void interactWith(PlayerEntity player, GameManipulator game);

    /**
     * Method for getting whether the object is passable or not.
     *
     * @return A boolean that determines whether the object is passable or not.
     */
    boolean isPassable();

    /**
     * Gets the location of the {@link TileObject}.
     *
     * @return A point that represents the location of the {@link TileObject}.
     */
    Point getLocation();
}