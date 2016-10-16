package crosstheborder.lib.tileobject;

import crosstheborder.lib.ImageFinder;
import crosstheborder.lib.Tile;
import crosstheborder.lib.enumeration.ObstacleType;
import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.Painter;
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
    private Tile tile;
    private ObstacleType type;

    /**
     * Creates a new Obstacle.
     * Sets isPassable to false;
     *
     * @param type The {@link ObstacleType} of the Obstacle.
     */
    public Obstacle(ObstacleType type) {
        this.type = type;
    }

    @Override
    public Point getLocation() {
        return this.tile.getLocation();
    }

    @Override
    public Tile getTile() {
        return this.tile;
    }

    @Override
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Explicitly left empty since an obstacle does not have an interaction.
     * {@inheritDoc}
     */
    @Override
    public boolean interactWith(PlayerEntity player, GameManipulator game) {
        return false;
    }

    @Override
    public boolean isAccessible(PlayerEntity entity) {
        return false;
    }

    @Override
    public int getCost(PlayerEntity entity) {
        if (!isAccessible(entity)) {
            return -1;
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Draws an obstacle with the image of it's obstacle type.
     */
    @Override
    public void draw(Painter painter, Point location, int tileWidth) {
        painter.drawImage(ImageFinder.getInstance().getImage(type), location, tileWidth, tileWidth);
    }
}
