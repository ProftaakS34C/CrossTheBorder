package crosstheborder.lib;

/**
 * Player is the super class for Trump and PlayerEntity.
 *
 * @author Oscar de Leeuw
 */
public abstract class Player {
    protected static final int SERVER_TICK_RATE = ServerSettings.getInstance().getServerTickRate();

    private String name;
    private Team team;

    /**
     * Abstract constructor that sets the name of the player.
     *
     * @param name The name of the player.
     * @param team The team the player is part of.
     */
    public Player(String name, Team team) {
        this.name = name;
        this.team = team;
        team.addTeamMember(this);
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
