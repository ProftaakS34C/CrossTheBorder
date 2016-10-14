package crosstheborder.lib;

import crosstheborder.lib.enumeration.TeamName;

import java.awt.*;
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
    private int scoreIncrease; //Amount of points to increase each time increaseScore is called.
    private List<Player> teamMembers = new ArrayList();
    private Rectangle teamArea;

    /**
     * Constructor of Team class.
     *
     * @param name Name of the team
     * @param scoreIncrease The amount the score of this team is increased by after each call of increaseScore.
     * @param teamArea The area of this team.
     */
    public Team(String name, Rectangle teamArea, int scoreIncrease) {
        this.name = TeamName.valueOf(name);
        this.score = 0;
        this.teamArea = teamArea;
        this.scoreIncrease = scoreIncrease;

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
     * Gets the area that belongs to this team.
     *
     * @return A rectangle object that represents the area.
     */
    public Rectangle getTeamArea() {
        return teamArea;
    }

    /**
     * Increases the score of this time.
     */
    public void increaseScore() {
        this.score += scoreIncrease;
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
