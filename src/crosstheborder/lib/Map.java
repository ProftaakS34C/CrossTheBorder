package crosstheborder.lib;

import crosstheborder.lib.interfaces.TileObject;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Oscar on 26-Sep-16.
 * The class map provides for the name , height and weight for the map.
 */
public class Map {

    Tile[][] tiles;
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
        tiles = new Tile[width][height];
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
     *
     * @param location The tile that is requested.
     * @return The tile at the given location.
     */
    public Tile getTile(Point location) {
        try {
            return tiles[location.x][location.y];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    /**
     * Check if there can be an object on this tile.
     *
     * @param location The location of the tile that will be checked.
     */
    public boolean canPlaceTileObject(Point location) {

        for(Tile t : tiles){

            if(t.getLocation().equals(location)){

                return t.hasTileObject();
            }
        }

        return false;
    }

    /**
     * Change a tile to a tile with an object.
     * @param location The location of the tile that has to be changed.
     * @param to The kind of tile that the tile has to become.
     */
    public void changeTileObject(Point location, TileObject to){

        tiles.stream().filter(x -> x.equals(location)).findFirst().get().setTileObject(to);
    }

    public boolean checkMoveDirection(Point point, MoveDirection direction){

        // Make new point, look in which direction the player is moving
        // And change the x or y value.
        Point p = null;

        if (direction.equals(MoveDirection.DOWN)) {

            p = new Point(point.x, point.y);

        } else if (direction.equals(MoveDirection.LEFT)) {

            p = new Point(point.x, point.y);

        } else if (direction.equals(MoveDirection.RIGHT)) {

            p = new Point(point.x, point.y);

        } else if (direction.equals(MoveDirection.UP)) {

            p = new Point(point.x, point.y);
        }

        // Look for the tile that corresponds
        for(Tile t : tiles) {

            if(t.getLocation().equals(p)){
                return t.isAccessable();
            }
        }
        return false;
    }

}
