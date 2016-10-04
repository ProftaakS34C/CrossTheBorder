package crosstheborder.lib;

import crosstheborder.lib.enumeration.TeamName;

/**
 * @author guillaime
 */
public class Team {
    private TeamName name;
    private int score;


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
     * Add 1 point to the score of a team.
     */
    public void increaseScore(){
        this.score++;
    }
}
