package crosstheborder.lib;

import crosstheborder.lib.interfaces.Camera;
import crosstheborder.lib.interfaces.Painter;

import java.awt.*;

/**
 * Implementation of the Camera interface.
 *
 * @author Oscar de Leeuw
 */
public class CameraImpl implements Camera {
    private Point center;
    private int tileWidth;
    private int cameraWidth;
    private int cameraHeight;
    private Tile[][] tiles;

    private int xTiles;
    private int yTiles;
    private int xStart;
    private int yStart;

    /**
     * Creates a new camera.
     *
     * @param center       The center of the camera.
     * @param tileWidth    The width of the tiles in pixels..
     * @param cameraWidth  The width of the camera in pixels.
     * @param cameraHeight The height of the camera in pixels.
     * @param tiles        The tiles of the map.
     */
    public CameraImpl(Point center, int tileWidth, int cameraWidth, int cameraHeight, Tile[][] tiles) {
        this.center = center;
        this.tileWidth = tileWidth;
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;
        this.tiles = tiles;

        this.xTiles = (int) Math.ceil((double) cameraWidth / tileWidth); //The amount of tiles along the x-axis.
        this.yTiles = (int) Math.ceil((double) cameraHeight / tileWidth); //The amount of tiles along the y-axis.
        this.xStart = center.x - xTiles / 2; //The column of the left most tiles.
        this.yStart = center.y - yTiles / 2; //The row of the top most tiles.
    }

    @Override
    public Point getLocationFromClick(int x, int y) {
        int xOffset = x / tileWidth;
        int yOffset = y / tileWidth;

        return new Point(xStart + xOffset, yStart + yOffset);
    }

    @Override
    public Point getCenterPoint() {
        return this.center;
    }

    @Override
    public int getTileWidth() {
        return this.tileWidth;
    }

    @Override
    public int getCameraWidth() {
        return this.cameraWidth;
    }

    @Override
    public int getCameraHeight() {
        return this.cameraHeight;
    }

    @Override
    public int getAmountOfHorizontalTiles() {
        return this.xTiles;
    }

    @Override
    public int getAmountOfVerticalTiles() {
        return this.yTiles;
    }

    @Override
    public void draw(Painter painter) {
        Point location = new Point(0, 0); //The current pixel location at which the image should be drawn.

        for (int x = xStart; x < x + xTiles && x < tiles.length; x++) {
            for (int y = yStart; y < y + yTiles && y < tiles[x].length; y++) {
                //Draw the tile at the current location.
                tiles[x][y].draw(painter, location, tileWidth);
                //Move the draw location tileWidth pixels down.
                location.translate(0, tileWidth);
            }
            //Move the draw location back to the top and tileWidth pixels to the right.
            location.translate(tileWidth, -cameraHeight);
        }
    }
}
