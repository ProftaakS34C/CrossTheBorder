package crosstheborder.lib.tileobject;

import crosstheborder.lib.interfaces.TileObject;

import java.awt.*;

/**
 * Represents a trap build by {@link crosstheborder.lib.player.Trump}.
 */
public class Trap implements TileObject {
    private boolean isPassable;
    private Point location;

    /**
     * Creates a new trap object.
     * Sets isPassable to true;
     *
     * @param location The location of the Trap.
     */
    public Trap(Point location) {
        isPassable = true;
        this.location = location;
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
