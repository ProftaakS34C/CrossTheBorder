package crosstheborder.lib;

import crosstheborder.lib.enumeration.TileType;
import crosstheborder.lib.interfaces.TileObject;

/**
 *  Represents a single tile that composes the map.
 *  The tile has a certain location.
 *  Can have a {@link TileObject} that fills the tile.
 *
 *  @author Oscar de Leeuw
 */
public class Tile {
    private TileObject tileObject;
    private TileType type;

    /**
     * Creates a new tile object with the given location.
     *
     * @param type The type of the tile.
     */
    public Tile(TileType type) {
        this.type = type;
    }

    /**
     * Returns whether the tile has a {@link TileObject} or not.
     *
     * @return True if the tile has a {@link TileObject}. False if it doesn't have a {@link TileObject}.
     */
    public boolean hasTileObject() {
        return tileObject != null;
    }

    /**
     * Returns the {@link TileObject} this tile has. Returns null if it does not have a {@link TileObject}.
     *
     * @return The {@link TileObject} object. Can be null.
     */
    public TileObject getTileObject() {
        return this.tileObject;
    }

    /**
     * Sets the {@link TileObject} for this tile.
     *
     * @param tileObject The object that fills the tile.
     */
    public void setTileObject(TileObject tileObject) {
        this.tileObject = tileObject;
    }
}
