package crosstheborder.lib;

import crosstheborder.lib.interfaces.GameSettings;

/**
 * Player is the super class for Trump and PlayerEntity.
 *
 * @author Oscar de Leeuw
 */
public abstract class Player {
    protected final int serverTickRate;

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

        serverTickRate = settings.getServerTickRate();
    }

    /**
     * Gets the team this player is apart of.
     *
     * @return A team object.
     */
    public Team getTeam() {
        return this.team;
    }

    @Override
    public String toString(){
        return name;
    }
}
