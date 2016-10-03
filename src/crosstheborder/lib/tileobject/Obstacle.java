package crosstheborder.lib.tileobject;

import crosstheborder.lib.interfaces.TileObject;

import java.awt.*;

/**
 * Represents an obstacle like a tree.
 */
public class Obstacle implements TileObject {
    private boolean isPassable;
    private Point location;

    /**
     * Creates a new Obstacle.
     * Sets isPassable to false;
     *
     * @param location The location of the Obstacle.
     */
    public Obstacle(Point location) {
        isPassable = false;
        this.location = location;
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

    /**
     * Gets the location of the {@link TileObject}.
     *
     * @return A point that represents the location of the {@link TileObject}.
     */
    @Override
    public Point getLocation() {
        return location;
    }
}
