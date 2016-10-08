package crosstheborder.lib.tileobject;

import crosstheborder.lib.enumeration.ObstacleType;
import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.PlayerEntity;

import java.awt.*;

/**
 * The Obstacle class represents a {@link TileObject} that is static and unplaceable by a {@link crosstheborder.lib.player.Trump}.
 * Obstacles are impassable by default.
 *
 * @author Oscar de Leeuw
 */
public class Obstacle implements TileObject {
    private boolean isPassable;
    private Point location;
    private ObstacleType type;

    /**
     * Creates a new Obstacle.
     * Sets isPassable to false;
     *
     * @param location The location of the Obstacle.
     * @param type The {@link ObstacleType} of the Obstacle.
     */
    public Obstacle(Point location, ObstacleType type) {
        this.isPassable = false;
        this.location = location;
        this.type = type;
    }

    /**
     * Explicitly left empty since an obstacle does not have an interaction.
     * {@inheritDoc}
     */
    @Override
    public void interactWith(PlayerEntity player, GameManipulator game) {
    }

    @Override
    public boolean isPassable() {
        return this.isPassable;
    }

    @Override
    public Point getLocation() {
        return this.location;
    }
}
