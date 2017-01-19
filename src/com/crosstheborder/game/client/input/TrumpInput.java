package com.crosstheborder.game.client.input;

import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderPlaceableType;
import com.sstengine.map.tile.Tile;
import com.sstengine.player.PlayerInput;

/**
 * @author Oscar de Leeuw
 */
public class TrumpInput implements PlayerInput {
    private CrossTheBorderPlaceableType type;
    private Tile tile;

    public TrumpInput(CrossTheBorderPlaceableType type, Tile tile) {
        this.type = type;
        this.tile = tile;
    }

    public CrossTheBorderPlaceableType getType() {
        return type;
    }

    public Tile getTile() {
        return tile;
    }
}
