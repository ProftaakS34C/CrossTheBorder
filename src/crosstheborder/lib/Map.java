package crosstheborder.lib;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Oscar on 26-Sep-16.
 * The class map provides for the name , height and weight for the map.
 */
public class Map {

    private String name;
    private int width;
    private int height;

    ArrayList<Tile> tiles;

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
     * Check if there can be an object on this tile.
     *
     * @param location The location of the tile that will be checked.
     */
    public void CanPlaceTileObject(Point location){
        throw new NotImplementedException();
    }

    /**
     * Change a tile to a tile with an object.
     *
     * @param location The location of the tile that has to be changed.
     * @param to The kind of tile that the tile has to become.
     */
    public void changeTileObject(Point location, TileObject to){
        throw new NotImplementedException();
    }

}
