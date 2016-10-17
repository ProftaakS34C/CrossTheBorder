package crosstheborder.lib.interfaces;

import java.awt.*;

/**
 * The TileObject class is an interface for objects that can live on {@link crosstheborder.lib.Tile}s within the game.
 *
 * @author Oscar de Leeuw
 */
public interface TileObject extends Drawable, Interactable {
    /**
     * Gets the location of the {@link TileObject}.
     * Will return the location of the tile this object belongs to.
     *
     * @return A point that represents the location of the {@link TileObject}.
     */
    Point getLocation();
}