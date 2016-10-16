package crosstheborder.lib;

import crosstheborder.lib.enumeration.Country;
import crosstheborder.lib.enumeration.Direction;
import crosstheborder.lib.interfaces.Camera;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.tileobject.Placeable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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
    public Tile getTile(Point location) {
        return getTile(location.x, location.y);
    }

    /**
     * Gets the tile from a given location.
     * Returns null when an {@link ArrayIndexOutOfBoundsException} happens.
     *
     * @param x The x-coordinate of the Tile
     * @param y The y-coordinate of the Tile.
     * @return The tile at the given location. Returns null when the given location is out of bounds.
     */
    public Tile getTile(int x, int y) {
        try {
            return tiles[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return null;
    }

    /**
     * Gets whether the given location is accessible for the given entity.
     *
     * @param location The location to check for accessibility.
     * @param entity The entity for which to check the accessibility.
     * @return True when the entity can enter the location.
     */
    public boolean isAccessible(Point location, PlayerEntity entity) {
        return getTile(location).isAccessible(entity);
    }

    /**
     * Gets a Tile inside an given area that is accessible to the given entity.
     * Can be an infinite loop.
     *
     * @param area The area out of which a point is requested.
     * @param entity The entity for which the tile should be accessible.
     * @return A tile that is free.
     */
    public Tile getFreeTileInArea(Rectangle area, PlayerEntity entity) {

        //Generate a random point within the given area.
        int x = ThreadLocalRandom.current().nextInt(area.x, area.x + area.width);
        int y = ThreadLocalRandom.current().nextInt(area.y, area.y + area.height);
        Tile nextLocation = getTile(x, y);

        //If the tile is occupied find a new location.
        if (entity == null || nextLocation.isAccessible(entity)) {
            return nextLocation;
        } else {
            return getFreeTileInArea(area, entity);
        }
    }

    /**
     * Gets a random tile in the given area.
     *
     * @param area The area out of which you want a tile.
     * @return A tile in the area.
     */
    public Tile getTileInArea(Rectangle area) {
        return getFreeTileInArea(area, null);
    }

    /**
     * Gets the neighbours of a location that are accessible to the given entity.
     *
     * @param location The location of which to get the neighbours.
     * @param entity The entity for which to check the accessibility.
     * @return A list of neighbours.
     */
    public List<Point> getNeighbours(Point location, PlayerEntity entity) {
        List<Point> ret = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            Point point = new Point(location.x + dir.getCartesianRepresentation().x, location.y + dir.getCartesianRepresentation().y);
            if (isAccessible(point, entity)) {
                ret.add(point);
            }
        }

        return ret;
    }

    /**
     * Determines whether a given Placeable can be placed on the map.
     *
     * @param tile  The tile the Placeable should be placed.
     * @param placeable The placeable for which to run this check.
     * @return True when the placeable can be placed at the given location.
     */
    public boolean canPlacePlaceable(Tile tile, Placeable placeable) {
        if (tile.getCountry() == Country.NONE && tile.hasTileObject()) {
            Point location = tile.getLocation(); //TODO Set this shit in a getNeighbours method.
            TileObject east = getTile(location.x + 1, location.y).getTileObject();
            TileObject west = getTile(location.x - 1, location.y).getTileObject();
            TileObject north = getTile(location.x, location.y - 1).getTileObject();
            TileObject south = getTile(location.x, location.y + 1).getTileObject();

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
