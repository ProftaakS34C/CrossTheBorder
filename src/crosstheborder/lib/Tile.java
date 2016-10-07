package crosstheborder.lib;

import crosstheborder.lib.interfaces.TileObject;

/**
 *  Represents a single tile that composes the map.
 *  The tile has a certain location.
 *  Can have a {@link TileObject} that fills the tile.
 */
public class Tile {
    private TileObject tileObject;

    /**
     * Creates a new tile object with the given location.
     */
    public Tile() {

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
     * Checks whether this tile is accessible or not.
     *
     * @return A boolean value that is true when the tile is accessible and false when it's inaccessible.
     */
    public boolean isAccessible() {
        //If the tile does not have a TileObject return true otherwise check from object.isPassable().
        return tileObject == null || tileObject.isPassable();
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
