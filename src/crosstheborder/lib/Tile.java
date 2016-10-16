package crosstheborder.lib;

import crosstheborder.lib.enumeration.Country;
import crosstheborder.lib.enumeration.TileType;
import crosstheborder.lib.interfaces.Drawable;
import crosstheborder.lib.interfaces.Painter;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.PlayerEntity;

import java.awt.*;
import java.io.File;

/**
 *  Represents a single tile that composes the map.
 *  The tile has a certain location.
 *  Can have a {@link TileObject} that fills the tile.
 *
 *  @author Oscar de Leeuw
 */
public class Tile implements Drawable {
    private TileObject tileObject;
    private PlayerEntity playerEntity;
    private TileType type;
    private Country country;
    private Point location;

    /**
     * Creates a new tile object with the given location, country and type.
     *
     * @param type The type of the tile.
     * @param country The country of this tile.
     * @param location The location of the tile.
     */
    public Tile(TileType type, Country country, Point location) {
        this.type = type;
        this.country = country;
        this.location = location;
    }

    /**
     * Gets the location of this tile.
     *
     * @return A point that represents the location of this tile.
     */
    public Point getLocation() {
        return this.location;
    }

    /**
     * Gets the country that this tile belongs to.
     *
     * @return
     */
    public Country getCountry() {
        return this.country;
    }

    /**
     * Gets the PlayerEntity that lives on this tile.
     *
     * @return The PlayerEntity object.
     */
    public PlayerEntity getPlayerEntity() {
        return this.playerEntity;
    }

    /**
     * Sets the PlayerEntity of this tile.
     *
     * @param playerEntity The PlayerEntity that should occupy this tile.
     */
    public void setPlayerEntity(PlayerEntity playerEntity) {
        if (playerEntity != null) {
            playerEntity.setTile(this);
        }
        this.playerEntity = playerEntity;
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
        if (tileObject != null) {
            tileObject.setTile(this);
        }
        this.tileObject = tileObject;
    }

    /**
     * Returns whether the tile has a {@link TileObject} or not.
     *
     * @return True if the tile has a {@link TileObject}. False if it doesn't have a {@link TileObject}.
     */
    public boolean hasTileObject() {
        return this.tileObject != null;
    }

    /**
     * Returns whether this tile has a PlayerEntity.
     *
     * @return A boolean that indicates whether this tile has a PlayerEntity.
     */
    public boolean hasPlayerEntity() {
        return this.playerEntity != null;
    }

    /**
     * Gets whether the given entity can access this tile.
     *
     * @param entity The entity for which to check the accessibility.
     * @return True when the entity can enter the tile.
     */
    public boolean isAccessible(PlayerEntity entity) {
        boolean tileObjectAccess = tileObject == null || tileObject.isAccessible(entity);
        boolean playerEntityAccess = playerEntity == null || playerEntity.isAccessible(entity);
        boolean countryAccess = country.isAccessible(entity);

        return countryAccess && tileObjectAccess && playerEntityAccess;
    }

    @Override
    public void draw(Painter painter, Point location, int tileWidth) {
        File file = ImageFinder.getInstance().getImage(type);
        painter.drawImage(file, location, tileWidth, tileWidth);

        country.draw(painter, location, tileWidth);

        if (hasTileObject()) {
            tileObject.draw(painter, location, tileWidth);
        }
        if (hasPlayerEntity()) {
            playerEntity.draw(painter, location, tileWidth);
        }
    }
}
