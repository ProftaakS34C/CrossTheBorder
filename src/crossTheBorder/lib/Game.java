package crossTheBorder.lib;

import crossTheBorder.lib.enums.Team;

/**
 * This class makes an instance of Game
 */
public class Game {

    private int scoreUSA;
    private int scoreMexico;

    public Game(){
        this.scoreUSA = 0;
        this.scoreMexico = 0;
    }

    public int getScoreUSA(){
        return scoreUSA;
    }

    public int getScoreMexico(){
        return scoreMexico;
    }

    public void setScore(Team teamName){
        if(teamName == Team.USA){
            scoreUSA++;
        }
        else if(teamName == Team.MEX){
            scoreMexico++;
        }
    }
}
