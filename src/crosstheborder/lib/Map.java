package crosstheborder.lib;

import crosstheborder.lib.interfaces.TileObject;

import java.awt.*;

/**
 * Created by Oscar on 26-Sep-16.
 * The class map provides for the name , height and weight for the map.
 */
public class Map {

    private Tile[][] tiles;
    private String name;
    private int width;
    private int height;

    /**
     * Constructor of Map class.
     *
     * @param name  The name of the map.
     * @param width  The width of the map.
     * @param height The height of the map.
     */

    public Map(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];

        generateMap();
    }

    private void generateMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.tiles[x][y] = new Tile();
            }
        }
    }

    /**
     * Gets the width of the map.
     *
     * @return The width of the map.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the map.
     *
     * @return The height of the map.
     */
    public int getHeight() {
        return height;
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
            e.printStackTrace(System.err);
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
     * Checks whether a given location is accessible.
     *
     * @param location The location that is being checked.
     * @return A boolean that is true when the location is accessible and false when it is not accessible.
     */
    public boolean isAccessible(Point location) {
        return getTile(location).isAccessible();
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


}
