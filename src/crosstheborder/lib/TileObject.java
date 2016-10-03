package crosstheborder.lib;

/**
 * Represents an object on a tile.
 */
public interface TileObject {

    /**
     * Gets whether this object is passable or not.
     *
     * @return A boolean that represents whether this object is passable.
     */
    boolean isPassable = false;

    /**
     * Method for handling the interaction between two {@link TileObject}s.
     *
     * @param o The TileObject that is interacting with this object.
     */
    void interactWith(TileObject o);
}