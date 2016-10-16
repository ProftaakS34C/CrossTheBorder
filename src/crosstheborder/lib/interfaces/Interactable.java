package crosstheborder.lib.interfaces;

import crosstheborder.lib.player.PlayerEntity;

/**
 * @author Oscar de Leeuw
 */
public interface Interactable {
    /**
     * Handles the interaction between the interactable and a PlayerEntity.
     *
     * @param entity The PlayerEntity that is interacting with the Interactable.
     * @param game   A GameManipulator on which interaction results can be executed.
     * @return True when further movement/interaction should be evaluated.
     */
    boolean interactWith(PlayerEntity entity, GameManipulator game);
}
