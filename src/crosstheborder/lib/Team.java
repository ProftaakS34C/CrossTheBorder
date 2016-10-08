package crosstheborder.lib;

import crosstheborder.lib.enumeration.TeamName;

import java.util.ArrayList;
import java.util.List;

/**
 * A team consists of a team name, a score and a list of teamMembers.
 *
 * @author guillaime
 * @author Oscar de Leeuw
 */
public class Team {
    private TeamName name;
    private int score;
    private List<Player> teamMembers = new ArrayList();


    /**
     * Constructor of Team class.
     *
     * @param name Name of the team
     */
    public Team(String name){
        this.name = TeamName.valueOf(name);
        this.score = 0;
    }

    /**
     * Gets score of team.
     *
     * @return score of team
     */
    public int getScore() {
        return score;
    }


    /**
     * Gets name of team.
     *
     * @return name of team.
     */
    public TeamName getName(){
        return name;
    }

    /**
     * Adds the given amount to the team score.
     * @param amount The amount to increase the score with.
     */
    public void increaseScore(int amount) {
        this.score += amount;
    }

    /**
     * Adds a player to the list of team members. Will not add the player if it is already present in the list.
     *
     * @param player The player that should be added to the list.
     */
    public void addTeamMember(Player player) {
        if (!teamMembers.contains(player)) {
            teamMembers.add(player);
        }
    }

    /**
     * Gets the list of all the players in this team.
     *
     * @return An ArrayList that is a copy of the internal ArrayList.
     */
    public List<Player> getTeamMembers() {
        return new ArrayList<>(teamMembers);
    }
}
