package crosstheborder.lib.tileobject.placeable;

import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.player.entity.Mexican;
import crosstheborder.lib.tileobject.Placeable;

import java.awt.*;

/**
 * Represents a wall that can be build by {@link crosstheborder.lib.player.Trump}.
 *
 * @author Oscar de Leeuw
 */
public class Wall extends Placeable {
    private boolean isPassable;
    private Point location;
    private int height;

    /**
     * Creates a new wall.
     * Sets isPassable to false.
     *
     * @param location The location of the wall.
     * @param height The height of the wall.
     */
    public Wall(Point location, int height) {
        super(location, false);
        this.height = height;
    }

    /**
     * Gets the height of the wall.
     * @return An integer that represents the height of the wall.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * {@inheritDoc}
     * <p>
     * If a {@link Mexican} can climb walls, start the wall climbing procedure.
     * </p>
     * Calls the following methods from GameManipulator:
     * <ul>
     *     <li>Calls {@link GameManipulator#changeTileObjectLocation(TileObject, Point)} when the PlayerEntity is a Mexican and the wall in successfully climbed.</li>
     * </ul>
     */
    @Override
    public void interactWith(PlayerEntity player, GameManipulator game) {
        if (player instanceof Mexican) {
            //If the mexican can scale walls
            if (((Mexican) player).climbWall(this)) {
                throwMexicanOverWall(player, game);
            }
        }
    }

    private void throwMexicanOverWall(PlayerEntity mexican, GameManipulator game) {
        //Gets the movement vector of the mexican and multiply by 2 to place him over the wall.
        int translationX = (mexican.getLocation().x - location.x) * 2;
        int translationY = (mexican.getLocation().y - location.y) * 2;

        Point newLocation = new Point(mexican.getLocation().x + translationX, mexican.getLocation().y + translationY);

        game.changeTileObjectLocation(mexican, newLocation);
    }
}
