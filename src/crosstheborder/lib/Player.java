package crosstheborder.lib;

import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.interfaces.GameSettings;

import java.awt.*;

/**
 * Player is the super class for Trump and PlayerEntity.
 *
 * @author Oscar de Leeuw
 */
public abstract class Player {
    protected final int serverTickRate;
    protected Point cameraLocation;
    private String name;
    private Team team;

    /**
     * Abstract constructor that sets the name of the player.
     *
     * @param name The name of the player.
     * @param team The team the player is part of.
     * @param settings The settings of the game.
     */
    public Player(String name, Team team, GameSettings settings) {
        this.name = name;
        this.team = team;
        team.addTeamMember(this);

        this.serverTickRate = settings.getServerTickRate();
    }

    /**
     * Gets the team this player is apart of.
     *
     * @return A team object.
     */
    public Team getTeam() {
        return this.team;
    }

    /**
     * Gets the location of a camera of a player.
     *
     * @return The location of the camera.
     */
    public abstract Point getCameraLocation();

    /**
     * Moves the location of a camera.
     * Does not work for PlayerEntities.
     *
     * @param md The direction the camera should move.
     */
    public abstract void moveCameraLocation(MoveDirection md);

    @Override
    public String toString(){
        return name;
    }
}
