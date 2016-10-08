package crosstheborder.lib;

import crosstheborder.lib.interfaces.TileObject;

import java.awt.*;

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
    private String name;

    private int width;
    private int height;

    private Tile[][] tiles;

    private Rectangle usaArea;
    private Rectangle mexicoArea;

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

        //Temp code for generating a map.
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.tiles[x][y] = new Tile();
            }
        }

        //Temp code for generating USA and mexico code.
        this.usaArea = new Rectangle(0, 0, width, height / 10);
        this.mexicoArea = new Rectangle(0, height - (height / 10), width, height / 10);
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
     * Changes a {@link Tile}'s {@link TileObject} to the given {@link TileObject}.
     *
     * @param location The location of the tile that has to be changed.
     * @param to The kind of tile that the tile has to become.
     */
    public void changeTileObject(Point location, TileObject to){
        getTile(location).setTileObject(to);
    }


}
