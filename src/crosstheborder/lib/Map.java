package crosstheborder.lib;

import crosstheborder.lib.interfaces.TileObject;

import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.tileobject.Wall;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Oscar on 26-Sep-16.
 * The class map provides for the name , height and weight for the map.
 */
public class Map {

    ArrayList<Tile> tiles;
    private String name;
    private int width;
    private int height;

    // This value is to give a tile a width, so the tile next to the first tile
    // will not overlap the other one.
    private int tilewidth = 10;

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
        tiles = new ArrayList<Tile>();

        /**
         * Fills the ArrayList with points.
         * For x < width, look if y < height and make an tile
         */
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                tiles.add(new Tile(new Point(x,y)));
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
     * Gives the width of a tile.
     *
     * @return The width of a tile
     */
    public int getTilewidth(){ return this.tilewidth; }

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

            p = new Point(point.x, point.y + tilewidth);

        } else if (direction.equals(MoveDirection.LEFT)) {

            p = new Point(point.x - tilewidth, point.y);

        } else if (direction.equals(MoveDirection.RIGHT)) {

            p = new Point(point.x + tilewidth, point.y);

        } else if (direction.equals(MoveDirection.UP)) {

            p = new Point(point.x, point.y - tilewidth);
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
