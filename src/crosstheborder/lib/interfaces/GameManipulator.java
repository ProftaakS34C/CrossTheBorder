package crosstheborder.lib.interfaces;

import crosstheborder.lib.Team;
import crosstheborder.lib.player.PlayerEntity;

import java.awt.*;

/**
 * An interface that holds an collections of methods that can be executed upon the game.
 * These methods are used in interactions between objects in the game.
 *
 * @author Oscar de Leeuw
 */
public interface GameManipulator {
    /**
     * Respawns a {@link PlayerEntity} within it's teams respawn area.
     *
     * @param player The {@link PlayerEntity} that should be respawned.
     */
    void respawnPlayer(PlayerEntity player);

    /**
     * Raises the score of a given {@link Team} by the given amount.
     * Amount can be negative.
     *
     * @param team   The {@link Team} that should have it's score increased.
     * @param amount The amount by which the score should be increased.
     */
    void increaseScore(Team team, int amount);

    /**
     * Changes the location of a {@link TileObject} to new location.
     * Will not execute if the location is not on the map.
     * Will remove the TileObject at the newLocation if there is one present.
     *
     * @param tileObject  The {@link TileObject} that should be moved.
     * @param newLocation The new location of the tileObject.
     */
    void changeTileObjectLocation(TileObject tileObject, Point newLocation);
}