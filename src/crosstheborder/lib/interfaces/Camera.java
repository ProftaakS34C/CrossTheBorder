package crosstheborder.lib.interfaces;

import java.awt.*;

/**
 * The camera is a subset of the map that is relevant for one client.
 *
 * @author Oscar de Leeuw
 */
public interface Camera {
    /**
     * Gets the center of the camera.
     *
     * @return A point that represents the center of the camera.
     */
    Point getCenterPoint();

    /**
     * Gets the location on the map from the camera of a certain pixel.
     *
     * @param x The x coordinate of the pixel.
     * @param y The y coordinate of the pixel.
     * @return A point that represents the location on the map.
     */
    Point getLocationFromClick(int x, int y);

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

    /**
     * Gets the amount of tiles along the x-axis.
     *
     * @return The amount of tiles along the x-axis.
     */
    int getAmountOfHorizontalTiles();

    /**
     * Gets the amount of tiles along the y-axis.
     *
     * @return The amount of tiles along the y-axis.
     */
    int getAmountOfVerticalTiles();

    /**
     * Draws a camera.
     *
     * @param painter The painter the camera should use to paint.
     */
    void draw(Painter painter);
}
