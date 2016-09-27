package crosstheborder.lib;

/**
 * Represents an object on a tile.
 */
public abstract class TileObject {

    private boolean isPassable;

    /**
     * Creates a new TileObject.
     */
    public TileObject(boolean isPassable) {
        this.isPassable = isPassable;
    }

    /**
     * Gets whether this object is passable or not.
     *
     * @return A boolean that represents whether this object is passable.
     */
    public boolean isPassable() {
        return isPassable;
    }

    /**
     * Method for handling the interaction between a player and a TileObject.
     *
     * @param player The player object that is interacting with the object.
     */
    public abstract void interactWith(Player player);
}