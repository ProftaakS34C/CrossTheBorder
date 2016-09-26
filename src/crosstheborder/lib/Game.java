package crosstheborder.lib;

import crosstheborder.lib.enums.MoveDirection;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class makes an instance of Game
 *
 * @author guillaime 
 */
public class Game {

    private ArrayList<Player> players;
    private Map map;
    private Team usa;
    private Team mex;

    /**
     * Constructor of Game class.
     */
    public Game(){
        usa = new Team("USA");
        mex = new Team("MEX");

        players = new ArrayList<>();
        map = new Map("The Border", 20, 20);

    }

    /**
     * Get the score of USA
     *
     * @return score of USA
     */
    public int getScoreUSA(){
        return usa.getScore();
    }

    /**
     * Get the score of MEX
     *
     * @return score of MEX
     */
    public int getScoreMexico(){
        return mex.getScore();
    }

    /**
     * Moves a player to certain position.
     *
     * @param direction Direction which the player is moving in.
     * @param point Point where the player is at the moment.
     * @return If player can move into this direction.
     */
    public boolean movePlayer(MoveDirection direction, Point point){
        throw new UnsupportedOperationException();
    }

    /**
     * Adds an obstacle
     *
     * @param location Location where the obstacle must be created.
     * @param player Will get checked if player is authorized.
     * @return If obstacle can e placed.
     */
    public boolean addObstacle(Point location, Player player){
        return checkForTrump(player);
    }

    /**
     * If a mexican gets into America or gets caught,
     * they will respawn on a selected tile.
     */
    public void respawnMexican(){
        throw new UnsupportedOperationException();
    }

    /**
     * Gets called after each timerTick. Updates the UI.
     */
    public void update(){
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if player is Trump
     *
     * @param player Gives a class that inherits player.
     * @return returns what player it is.
     */
    private boolean checkForTrump(Player player){
        return true;
    }
}