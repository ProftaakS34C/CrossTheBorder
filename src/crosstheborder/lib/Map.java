package crosstheborder.lib;

import crosstheborder.lib.interfaces.Camera;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.tileobject.Placeable;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Map class represents a collection of tiles that form a map within the CrossTheBorder game.
 *
 * A map is always created from a file with the .ctbmap extension.
 * This file contains the width and the height of the map, the type of tiles that compose the map,
 * the objects that exist on the map and the areas for the Mexican and USA teams.
 *
 * @author Oscar de Leeuw
 */
public class Map {
    private static final Logger LOGGER = Logger.getLogger(Map.class.getName());

    private String name;

    private int width;
    private int height;

    private Tile[][] tiles;

    private Rectangle usaArea;
    private Rectangle mexicoArea;

    private Map(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.usaArea = builder.usaArea;
        this.mexicoArea = builder.mexicoArea;
        this.name = builder.name;
        this.tiles = builder.tiles;
    }

    /**
     * Gets the width of the map.
     *
     * @return The width of the map.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Gets the height of the map.
     *
     * @return The height of the map.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Gets the area that is considered USA.
     *
     * @return A rectangle object that represents the USA area.
     */
    public Rectangle getUsaArea() {
        return this.usaArea;
    }

    /**
     * Get the area that is considered Mexican.
     *
     * @return A rectangle object that represents the Mexican area.
     */
    public Rectangle getMexicoArea() {
        return this.mexicoArea;
    }

    /**
     * Gets a camera/viewport of the map.
     *
     * @param center       The center of the camera.
     * @param tileWidth    The width of the tiles in the camera in pixels.
     * @param cameraWidth  The width of the camera in pixels.
     * @param cameraHeight The height of the camera in pixels.
     * @return A camera object.
     */
    public Camera getCamera(Point center, int tileWidth, int cameraWidth, int cameraHeight) {
        return new CameraImpl(center, tileWidth, cameraWidth, cameraHeight, tiles);
    }

    /**
     * Gets the tile from a given location.
     * Returns null when an {@link ArrayIndexOutOfBoundsException} happens.
     *
     * @param location The location of the requested tile.
     * @return The tile at the given location. Returns null when the given location is out of bounds.
     */
    private Tile getTile(Point location) {
        try {
            return tiles[location.x][location.y];
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return null;
    }

    /**
     * Gets a {@link TileObject} from a {@link Tile} with a given location.
     *
     * @param location A point that represents the location of a tile.
     * @return The TileObject at the given location. Returns null when there is no tileObject.
     */
    public TileObject getTileObject(Point location) {
        return getTile(location).getTileObject();
    }

    /**
     * Checks whether a given position has a tile object.
     *
     * @param location The location of the tile that will be checked.
     */
    public boolean hasTileObject(Point location) {
        return getTile(location).hasTileObject();
    }

    /**
     * Changes a {@link Tile}'s {@link TileObject} to the given {@link TileObject}.
     *
     * @param location The location of the tile that has to be changed.
     * @param to The kind of tile that the tile has to become.
     */
    public void changeTileObject(Point location, TileObject to){
        getTile(location).setTileObject(to);
    }

    /**
     * Gets a point devoid of a TileObject in a given area.
     * Can be an infinite loop.
     *
     * @param area The area out of which a point is requested.
     * @return A point that does not contain a TileObject in a given area.
     */
    public Point getFreePointInArea(Rectangle area) {

        //Generate a random point within the given area.
        int x = ThreadLocalRandom.current().nextInt(area.x, area.x + area.width);
        int y = ThreadLocalRandom.current().nextInt(area.y, area.y + area.height);
        Point nextLocation = new Point(x, y);

        //If the tile is occupied find a new location.
        if (hasTileObject(nextLocation)) {
            return getFreePointInArea(area);
        } else {
            return nextLocation;
        }
    }

    public boolean canPlacePlaceable(Point location, Placeable placeable) {
        if (!mexicoArea.contains(location) && !usaArea.contains(location) && !hasTileObject(location)) {
            TileObject east = getTileObject(new Point(location.x + 1, location.y));
            TileObject west = getTileObject(new Point(location.x - 1, location.y));
            TileObject north = getTileObject(new Point(location.x, location.y - 1));
            TileObject south = getTileObject(new Point(location.x, location.y + 1));

            return placeable.canPlaceWithNeighbours(east, west, north, south);
        }

        return false;
    }

    /**
     * Builds a map class.
     */
    public static class Builder {
        private String name;
        private int width;
        private int height;
        private Rectangle usaArea;
        private Rectangle mexicoArea;
        private Tile[][] tiles;

        /**
         * Makes a map with the given name.
         *
         * @param name The name of the map.
         */
        public Builder(String name) {
            this.name = name;
        }

        /**
         * Sets the width of the map.
         *
         * @param width The width of the map.
         * @return This builder object.
         */
        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        /**
         * Sets the height of the map.
         *
         * @param height The height of the map.
         * @return This builder object.
         */
        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        /**
         * The area of the USA team.
         *
         * @param area The area of the USA team.
         * @return This builder object.
         */
        public Builder setUsaArea(Rectangle area) {
            this.usaArea = area;
            return this;
        }

        /**
         * Sets the area of the Mexico team.
         *
         * @param area The area of the Mexico team.
         * @return This builder object.
         */
        public Builder setMexicoArea(Rectangle area) {
            this.mexicoArea = area;
            return this;
        }

        /**
         * Sets the tiles of the map.
         *
         * @param tiles A two dimensional array of tiles.
         * @return This builder object.
         */
        public Builder setTiles(Tile[][] tiles) {
            this.tiles = tiles;
            return this;
        }

        /**
         * Builds the map.
         *
         * @return A map object.
         */
        public Map build() {
            if (tiles == null || mexicoArea == null || usaArea == null || name == null || width == 0 || height == 0) {
                throw new IllegalArgumentException("All properties of the map need to be initialized.");
            }

            return new Map(this);
        }
    }
}
