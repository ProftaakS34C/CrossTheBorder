package crosstheborder.lib.interfaces;

import crosstheborder.lib.Team;
import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.enumeration.PlaceableType;
import crosstheborder.lib.player.PlayerEntity;

import java.awt.*;

/**
 * Interface for a game.
 * Supports pulling information from the game and pushing input to the game.
 *
 * @author Oscar de Leeuw
 */
public interface GameInterface {
    /**
     * Gets a camera from the map.
     *
     * @param center       The center of the camera.
     * @param tileWidth    The width of the tiles in pixels.
     * @param cameraWidth  The width of the camera in pixels.
     * @param cameraHeight The height of the camera in pixels.
     * @return A camera of the map of the game.
     */
    Camera getCamera(Point center, int tileWidth, int cameraWidth, int cameraHeight);

    /**
     * Gets the USA team.
     *
     * @return A team object representing the USA team.
     */
    Team getUsa();

    /**
     * Gets the Mexican team.
     *
     * @return A team object representing the Mexican team.
     */
    Team getMexico();

    /**
     * Gets the remaining time remaining in seconds.
     *
     * @return The remaining time.
     */
    int getRemainingTime();

    /**
     * Sends input to a specific player.
     *
     * @param md     The direction of the movement.
     * @param player The player the move is designed for.
     */
    void sendMoveInput(MoveDirection md, PlayerEntity player);

    /**
     * Adds a placeable to the specified location.
     *
     * @param location  The location the placeable should be located.
     * @param placeableType The type of placeable that should be placed.
     */
    void addPlaceable(Point location, PlaceableType placeableType);
}
