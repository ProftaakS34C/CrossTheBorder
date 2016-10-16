package crosstheborder.lib.interfaces;

import crosstheborder.lib.Team;
import crosstheborder.lib.Tile;
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
     */
    void increaseScore(Team team);

    /**
     * Changes the location of a PlayerEntity to new location.
     * Will not execute if the location is not on the map.
     * Will remove the PlayerEntity at the newLocation if there is one present.
     *
     * @param entity  The PlayerEntity that should be moved.
     * @param newLocation The new location of the tileObject.
     */
    void changePlayerEntityLocation(PlayerEntity entity, Tile newLocation);

    /**
     * Changes the location of a TileObject to new location.
     * Will not execute if the location is not on the map.
     * Will remove the TileObject at the newLocation if there is one present.
     *
     * @param object      The TileObject that should be moved.
     * @param newLocation The new location of the tileObject.
     */
    void changeTileObjectLocation(TileObject object, Tile newLocation);

    /**
     * Removes a TileObject from the game.
     *
     * @param tileObject The TileObject that should be removed.
     */
    void removeTileObject(TileObject tileObject);

    /**
     * Moves a player entity to the given location if possible.
     * If there is a tile object at the other location interact with it.
     *
     * @param player       The player that should be moved.
     * @param nextLocation The location the player wants to move to.
     */
    void movePlayerEntity(PlayerEntity player, Point nextLocation);
}
