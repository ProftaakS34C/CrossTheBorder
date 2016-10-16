package crosstheborder.lib.interfaces;

import crosstheborder.lib.player.PlayerEntity;

import java.awt.*;

/**
 * The TileObject class is an interface for objects that can live on {@link crosstheborder.lib.Tile}s within the game.
 *
 * @author Oscar de Leeuw
 */
public interface TileObject extends Drawable, Interactable {
    /**
     * {@inheritDoc}
     *
     * Method for handling the interaction between a {@link PlayerEntity} and a {@link TileObject}.
     * Calls methods on the {@link GameManipulator} object to process interaction results.
     */
    boolean interactWith(PlayerEntity player, GameManipulator game);

    /**
     * Gets the location of the {@link TileObject}.
     *
     * @return A point that represents the location of the {@link TileObject}.
     */
    Point getLocation();
}