package crosstheborder.lib.enumeration;

import crosstheborder.lib.MapLoader;

/**
 * The TileType enum captures what type of tile a tile is.
 * Used to determine what texture to draw for a tile.
 *
 * @author Oscar de Leeuw
 */
public enum TileType {
    Dirt("d"), Sand("s"), Grass("g");

    TileType(String code) {
        MapLoader.getInstance().registerTileTypeCode(code, this);
    }
}
