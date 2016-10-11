package crosstheborder.lib.tileobject.placeable;

import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.GameSettings;
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
    private int height;

    /**
     * Creates a new wall.
     *
     * @param settings The settings of the game.
     */
    public Wall(GameSettings settings) {
        this.height = settings.getDefaultWallHeight();
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

    /**
     * {@inheritDoc}
     * <p>
     * Returns true with the following cases:
     * <ul>
     * <li>There are no walls around it.</li>
     * <li>There is 1 wall around it.</li>
     * <li>There is a wall to it's east and west.</li>
     * <li>There is a wall to it's north and south.</li>
     * </ul>
     */
    @Override
    public boolean canPlaceWithNeighbours(TileObject east, TileObject west, TileObject north, TileObject south) {
        //Boolean expression = !E*!W + !N*!S.
        return (!(east instanceof Wall) && !(west instanceof Wall)) || (!(north instanceof Wall) && !(south instanceof Wall));
    }

    private void throwMexicanOverWall(PlayerEntity mexican, GameManipulator game) {
        //Gets the movement vector of the mexican and multiply by 2 to place him over the wall.
        int translationX = (mexican.getLocation().x - getLocation().x) * 2;
        int translationY = (mexican.getLocation().y - getLocation().y) * 2;

        Point newLocation = new Point(mexican.getLocation().x + translationX, mexican.getLocation().y + translationY);

        game.movePlayerEntity(mexican, newLocation);
    }
}
