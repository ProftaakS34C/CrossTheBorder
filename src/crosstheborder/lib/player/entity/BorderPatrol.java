package crosstheborder.lib.player.entity;

import crosstheborder.lib.Team;
import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.GameSettings;
import crosstheborder.lib.player.PlayerEntity;

import java.awt.*;

/**
 * This class represents the Border Patrol player object.
 *
 * @author Joram
 * @author Oscar de Leeuw
 * @version 1.0
 */
public class BorderPatrol extends PlayerEntity {

    /**
     * This is the constructor of the {@link BorderPatrol} class.
     * Calls the {@link PlayerEntity#PlayerEntity(String, Team, GameSettings)} constructor.
     * Sets isPassable to false;
     *
     * @param name The name of the player.
     * @param team The team this BorderPatrol is part of.
     * @param settings The settings of the game.
     */
    public BorderPatrol(String name, Team team, GameSettings settings) {
        super(name, team, settings);
    }

    /**
     * {@inheritDoc}
     * <p>
     * If the other playerEntity is a {@link Mexican}, respawn the Mexican and raise the score of the BorderPatrol team.
     * </p>
     * Calls the following methods from GameManipulator:
     * <ul>
     * <li>{@link GameManipulator#increaseScore(Team)} when the other entity is a Mexican.</li>
     * <li>{@link GameManipulator#respawnPlayer(PlayerEntity)} with the Mexican when the other entity is a Mexican.</li>
     * <li>{@link GameManipulator#changePlayerEntityLocation(PlayerEntity, Point)} With itself to the location of the Mexican.</li>
     * </ul>
     */
    @Override
    public void interactWith(PlayerEntity player, GameManipulator game) {
        if (player instanceof Mexican) {
            game.increaseScore(getTeam());
            game.respawnPlayer(player);
            game.changePlayerEntityLocation(this, player.getLocation());
        }
    }
}