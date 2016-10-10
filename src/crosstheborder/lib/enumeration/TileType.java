package crosstheborder.lib.enumeration;

/**
 * The TileType enum captures what type of tile a tile is.
 * Used to determine what texture to draw for a tile.
 *
 * @author Oscar de Leeuw
 */
public enum TileType {
    DIRT, SAND, GRASS;

    static {
        DIRT.code = 'd';
        SAND.code = 's';
        GRASS.code = 'g';
    }

    /**
     * The code for this TileType in the .ctbmap format.
     */
    private char code;

    /**
     * The code for the tile type in the .ctbmap file format.
     *
     * @return The code of the tile type.
     */
    public char getCode() {
        return this.code;
    }
}
