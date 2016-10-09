package crosstheborder.lib.tileobject;

import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.PlayerEntity;

import java.awt.*;

/**
 * The Placeable class represents a {@link TileObject} that can be placed by {@link crosstheborder.lib.player.Trump}.
 *
 * @author Oscar de Leeuw
 */
public abstract class Placeable implements TileObject {
    private Point location;
    private boolean isPassable;

    /**
     * Creates a new placeable with the given location and isPassable status.
     *
     * @param location   A {@link Point} that represents the location of the object.
     * @param isPassable A boolean that represents the passability of the object.
     */
    protected Placeable(Point location, boolean isPassable) {
        this.location = location;
        this.isPassable = isPassable;
    }

    public abstract void interactWith(PlayerEntity player, GameManipulator game);

    @Override
    public Point getLocation() {
        return this.location;
    }
}
