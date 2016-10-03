package crosstheborder.lib.tileobject;

import crosstheborder.lib.TileObject;

/**
 * Represents a wall that can be build by {@link crosstheborder.lib.player.Trump}.
 */
public class Wall implements TileObject {
    private boolean isPassable;

    /**
     * Creates a new wall.
     * Sets isPassable to false.
     */
    public Wall() {
        isPassable = false;
    }

    /**
     * Method for handling the interaction between two {@link TileObject}s.
     *
     * @param o The TileObject that is interacting with this object.
     */
    @Override
    public void interactWith(TileObject o) {
        o.interactWith(this);
    }

    /**
     * Method for getting whether the object is passable or not.
     *
     * @return A boolean that determines whether the object is passable or not.
     */
    @Override
    public boolean isPassable() {
        return false;
    }
}
