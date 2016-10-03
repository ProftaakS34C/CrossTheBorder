package crosstheborder.lib.player.entity;

import crosstheborder.lib.enumeration.TileObjectType;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.PlayerEntity;

import java.awt.*;

/**
 * This class represents the Border Patrol player object.
 * Extends the {@link PlayerEntity} class.
 * @author Joram
 * @version 1.0
 */
public class BorderPatrol extends PlayerEntity {

    /**
     * This is the constructor of the {@link BorderPatrol} class.
     * Calls the {@link PlayerEntity#PlayerEntity(String, Point)} constructor.
     * Sets isPassable to false;
     *
     * @param name The name of the player.
     * @param location The location of the player.
     */
    public BorderPatrol(String name, Point location) {
        super(name, location);
        isPassable = false;
    }

    /**
     * Method for handling the interaction between two {@link TileObject}s.
     *
     * @param o The TileObject that is interacting with this object.
     */
    @Override
    public void interactWith(TileObject o) {
        if (o.isPassable()) {
            this.location = o.getLocation();
        }

        TileObjectType type = TileObjectType.valueOf(o.getClass().getSimpleName());
        switch (type) {
            case Mexican:
                //TODO add interaction between mexican and borderpatrol.
                break;
        }
    }

    /**
     * Method for getting whether the object is passable or not.
     *
     * @return A boolean that determines whether the object is passable or not.
     */
    @Override
    public boolean isPassable() {
        return this.isPassable;
    }

    /**
     * Gets the location of the {@link TileObject}.
     *
     * @return A point that represents the location of the {@link TileObject}.
     */
    @Override
    public Point getLocation() {
        return this.location;
    }
}