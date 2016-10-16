package crosstheborder.lib.enumeration;

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

    /**
     * {@inheritDoc}
     * <p>
     * Handles the interaction between a country and an entity.
     */
    public boolean interactWith(PlayerEntity entity, GameManipulator game) {
        switch (this) {
            case USA:
                if (entity.getTeam().getName() == TeamName.USA) {
                    return true;
                } else if (entity.getTeam().getName() == TeamName.MEX) {
                    game.increaseScore(entity.getTeam());
                    game.respawnPlayer(entity);
                    return false;
                }
            case MEX:
                if (entity.getTeam().getName() == TeamName.USA) {
                    return false;
                }
            case NONE:
            default:
                return true;
        }
    }

    @Override
    public void draw(Painter painter, Point location, int tileWidth) {
        //TODO Add a draw method for painter.
    }
}
