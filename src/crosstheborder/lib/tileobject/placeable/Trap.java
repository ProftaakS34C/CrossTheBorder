package crosstheborder.lib.tileobject.placeable;

import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.GameSettings;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.player.entity.Mexican;
import crosstheborder.lib.tileobject.Placeable;

import java.awt.*;

/**
 * Represents a trap that can be placed by {@link crosstheborder.lib.player.Trump}.
 *
 * @author Oscar de Leeuw
 */
public class Trap extends Placeable {
    //The time the trap will immobilize a Mexican in seconds.
    private int trapTime;

    /**
     * Creates a new trap object.
     *
     * @param settings The settings of the game.
     */
    public Trap(GameSettings settings) {
        this.trapTime = settings.getDefaultTrapTime();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Traps a PlayerEntity if it is a {@link Mexican} for the amount of time stored in trapTime.
     * Will not allow a {@link crosstheborder.lib.player.entity.BorderPatrol} to move to the tile of the trap.
     * </p>
     * Calls the following methods from GameManipulator:
     * <ul>
     *     <li>Calls {@link GameManipulator#changeTileObjectLocation(TileObject, Point)} when the PlayerEntity is a Mexican.</li>
     * </ul>
     */
    @Override
    public void interactWith(PlayerEntity player, GameManipulator game) {
        //Trap the player if it is a mexican.
        if (player instanceof Mexican) {
            player.immobilize(trapTime);
            //Removes the trap and relocates the player to the location of the trap.
            game.changeTileObjectLocation(player, this.getLocation());
        }


    }

    /**
     * {@inheritDoc}
     * <p>
     * Will only return true when none of it's neighbours are traps.
     */
    @Override
    public boolean canPlaceWithNeighbours(TileObject east, TileObject west, TileObject north, TileObject south) {
        return !(east instanceof Trap || west instanceof Trap || north instanceof Trap || south instanceof Trap);
    }
}
