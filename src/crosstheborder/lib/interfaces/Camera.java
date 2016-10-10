package crosstheborder.lib.interfaces;

import java.awt.*;

/**
 * The camera is a subset of the map that is relevant for one client.
 *
 * @author Oscar de Leeuw
 */
public interface Camera extends Drawable {
    /**
     * Gets the center of the camera.
     *
     * @return A point that represents the center of the camera.
     */
    Point getCenterPoint();

    /**
     * Gets the width (and height) of a tile.
     *
     * @return The width of the tile in pixels.
     */
    int getTileWidth();

    /**
     * Gets the width of the entire camera.
     *
     * @return The width of the camera in pixels.
     */
    int getCameraWidth();

    /**
     * Gets the height of the entire camera.
     *
     * @return The height of the camera in pixels.
     */
    int getCameraHeight();
}
