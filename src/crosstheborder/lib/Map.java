package crosstheborder.lib;

import crosstheborder.lib.enums.MoveDirection;
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

        int x = 0;

        for(int ix = width; ix > 0; ix--){

            int y = 0;
            tiles.add(new Tile(new Point(x, y)));

            for(int iy = height -1; iy > 0; iy--){

                y += tilewidth;
                tiles.add(new Tile(new Point(x, y)));
            }

            x += tilewidth;
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

                if(t.hasTileObject()){
                    return false;
                }
                else{
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Change a tile to a tile with an object.
     *  @param location The location of the tile that has to be changed.
     * @param to The kind of tile that the tile has to become.
     */
    public void changeTileObject(Point location, TileObject to){

        for (Tile t : tiles) {

            if(t.getLocation().equals(location)){

                int index = tiles.indexOf(t);
                Tile temp = new Tile(location);
                temp.setTileObject(to);

                tiles.set(index, temp);
            }
        }
    }

    public boolean checkMoveDirection(Point point, MoveDirection direction){

        Point p = null;

        for(Tile t : tiles) {

            if (direction.equals(MoveDirection.DOWN)) {

                p = new Point(point.x, point.y + tilewidth);

            } else if (direction.equals(MoveDirection.LEFT)) {

                p = new Point(point.x - tilewidth, point.y);

            } else if (direction.equals(MoveDirection.RIGHT)) {

                p = new Point(point.x + tilewidth, point.y);

            } else if (direction.equals(MoveDirection.UP)) {

                p = new Point(point.x, point.y - tilewidth);
            }

            if(t.getLocation().equals(p)){

                if(t.isAccessable()){
                    return true;
                }
            }
        }

        return false;
    }

}
