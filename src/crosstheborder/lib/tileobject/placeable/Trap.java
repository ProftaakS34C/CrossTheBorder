package crosstheborder.lib.tileobject.placeable;

import crosstheborder.lib.enumeration.Country;
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
    private int trapUses;

    /**
     * Creates a new trap object.
     * Modification of the constructor requires editing of the {@link crosstheborder.lib.enumeration.PlaceableType#getPlaceable(GameSettings)} method.
     *
     * @param settings The settings of the game.
     */
    public Trap(GameSettings settings) {
        this.trapTime = settings.getDefaultTrapTime();
        this.trapUses = 1; //TODO set this in gameSettings.
    }

    /**
     * {@inheritDoc}
     * <p>
     * Traps a PlayerEntity if it is a {@link Mexican} for the amount of time stored in trapTime.
     * Will not allow a {@link crosstheborder.lib.player.entity.BorderPatrol} to move to the tile of the trap.
     * </p>
     * Calls the following methods from GameManipulator:
     * <ul>
     *     <li>Calls {@link GameManipulator#changePlayerEntityLocation(PlayerEntity, Point)} when the PlayerEntity is a Mexican.</li>
     *     <li>Calls {@link GameManipulator#removeTileObject(TileObject)} with itself if the trap has no uses left.</li>
     * </ul>
     */
    @Override
    public boolean interactWith(PlayerEntity player, GameManipulator game) {
        //Trap the player if it is a mexican.
        if (player.getTeam().getCountry() == Country.MEX) {
            player.immobilize(trapTime);

            trapUses--;
            if (trapUses == 0) {
                game.removeTileObject(this);
            }
        }
        return true;
    }

    @Override
    public boolean isAccessible(PlayerEntity entity) {
        return true;
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
