package crosstheborder.lib.interfaces;

import java.awt.*;

/**
 * Represents an object on a tile.
 */
public interface TileObject {
    /**
     * Method for handling the interaction between two {@link TileObject}s.
     *
     * @param o The TileObject that is interacting with this object.
     */
    void interactWith(TileObject o);

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