package crosstheborder.lib.tileobject;

import crosstheborder.lib.ImageFinder;
import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.Painter;
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

    /**
     * Creates a new placeable with the given location and isPassable status.
     */
    protected Placeable() {
        this.location = new Point();
    }

    public abstract void interactWith(PlayerEntity player, GameManipulator game);

    @Override
    public Point getLocation() {
        return this.location;
    }

    @Override
    public void draw(Painter painter, Point location, int tileWidth) {
        painter.drawImage(ImageFinder.getInstance().getImage(this), location, tileWidth, tileWidth);
    }
}
