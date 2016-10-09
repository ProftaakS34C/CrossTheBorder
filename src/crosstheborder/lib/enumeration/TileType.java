package crosstheborder.lib.enumeration;

/**
 * The TileType enum captures what type of tile a tile is.
 * Used to determine what texture to draw for a tile.
 *
 * @author Oscar de Leeuw
 */
public enum TileType {
    Dirt, Sand, Grass;

    static {
        Dirt.code = 'd';
        Sand.code = 's';
        Grass.code = 'g';
    }

    /**
     * The code for this TileType in the .ctbmap format.
     */
    public char code;
}
