package crosstheborder.lib.enumeration;

import crosstheborder.lib.Team;
import crosstheborder.lib.Tile;
import crosstheborder.lib.interfaces.Drawable;
import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.Interactable;
import crosstheborder.lib.interfaces.Painter;
import crosstheborder.lib.player.PlayerEntity;

import java.awt.*;

/**
 * Enum for capturing the Countries that can exist within the game.
 *
 * @author Oscar de Leeuw
 */
public enum Country implements Drawable, Interactable {
    USA, MEX, NONE;

    private Tile tile;

    /**
     * {@inheritDoc}
     * This should never be called. Currently not implemented.
     */
    @Override
    public Tile getTile() {
        return this.tile;
    }

    /**
     * {@inheritDoc}
     * This should never be called. Currently not implemented.
     */
    @Override
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handles the interaction between a country and an entity.
     * <p>
     *     Calls the follow methods on GameManipulator:
     *     <ul>
     *         <li>Calls: {@link GameManipulator#increaseScore(Team)} when a MEX enters the USA.</li>
     *         <li>Calls: {@link GameManipulator#respawnPlayer(PlayerEntity)} when a MEX enters the USA.</li>
     *     </ul>
     * </p>
     * Will return false when a Mexican has scored.
     */
    @Override
    public boolean interactWith(PlayerEntity entity, GameManipulator game) {
        switch (this) {
            case USA:
                if (entity.getTeam().getCountry() == MEX) {
                    game.increaseScore(entity.getTeam());
                    game.respawnPlayer(entity);
                    return false;
                }
        }

        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Will only return false when an USA entity tries to enter MEX.
     */
    @Override
    public boolean isAccessible(PlayerEntity entity) {
        switch (this) {
            case MEX:
                if (entity.getTeam().getCountry() == USA) {
                    return false;
                }
        }

        return true;
    }

    @Override
    public void draw(Painter painter, Point location, int tileWidth) {
        //TODO Add a draw method for painter.
    }
}
