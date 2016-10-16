package crosstheborder.lib.interfaces;

import crosstheborder.lib.player.PlayerEntity;

/**
 * Interface that indicates that a Class can interact with PlayerEntities.
 * All classes that implement Interactable should be present on the map.
 *
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

    /**
     * Gets whether the given entity can move on top of this Interactable.
     *
     * @param entity The entity for which to check the accessibility.
     * @return True when the entity can enter the tile of the Interactable.
     */
    boolean isAccessible(PlayerEntity entity);
}
