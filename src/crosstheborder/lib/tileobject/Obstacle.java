package crosstheborder.lib.tileobject;

import crosstheborder.lib.TileObject;

/**
 * Represents an obstacle like a tree
 */
public class Obstacle implements TileObject {
    private boolean isPassable;

    /**
     * Creates a new TileObject.
     */
    public Obstacle() {
        isPassable = false;
    }

    /**
     * Method for handling the interaction between two {@link TileObject}s.
     *
     * @param o The TileObject that is interacting with this object.
     */
    public void interactWith(TileObject o) {
        //Other object should be a player so have the player interact with the tileobject.
        o.interactWith(this);
    }

    /**
     * Method for getting whether the object is passable or not.
     *
     * @return A boolean that determines whether the object is passable or not.
     */
    @Override
    public boolean isPassable() {
        return isPassable;
    }
}
