package crosstheborder.lib;

import crosstheborder.lib.interfaces.Camera;
import crosstheborder.lib.interfaces.Painter;

import java.awt.*;

/**
 * @author Oscar de Leeuw
 */
public class CameraImpl implements Camera {
    private Point center;
    private int tileWidth;
    private int cameraWidth;
    private int cameraHeight;
    private Tile[][] tiles;

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
    public void draw(Painter painter) {
        int xTiles = (int) Math.ceil(cameraWidth / tileWidth); //The amount of tiles along the x-axis.
        int yTiles = (int) Math.ceil(cameraHeight / tileWidth); //The amount of tiles along the y-axis.
        //int x = center.x - (xTiles/2); //The left-most tile that should be drawn.
        //int y = center.y - (yTiles/2); //The upper-most tile that should be drawn.
        Point location = new Point(0, 0); //The current pixel location at which the image should be drawn.

        for (int x = center.x - xTiles / 2; x < x + xTiles; x++) {
            for (int y = center.y - yTiles / 2; y < y + yTiles; y++) {
                tiles[x][y].draw(painter, location, tileWidth);
                location.translate(0, tileWidth);
            }
            location.translate(tileWidth, -cameraHeight);
        }
    }
}
