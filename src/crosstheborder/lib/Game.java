package crosstheborder.lib;

import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.player.Trump;
import crosstheborder.lib.player.entity.BorderPatrol;
import crosstheborder.lib.player.entity.Mexican;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

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
     * int that shows on what Y-coordinate USA and MEX start.
     * With this values you can check if the mexican is in USA to respawn
     * also we can see if the BorderPatrol is not going into MEX.
     */
    private int usaY;
    private int mexY;

    /**
     * Constructor of Game class.
     */
    public Game(){
        usa = new Team("USA");
        mex = new Team("MEX");
        players = new ArrayList<>();

        // For now we use 20 as width and height, this can be changed if we want to.
        map = new Map("The Border", 20, 20);


        // The numbers 6 and 4 are chosen to devide the map in 3 pieces.
        usaY = map.getHeight() / 6;
        mexY = map.getHeight() / 4;
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
     * @return If player can move into this direction.
     */
    public boolean movePlayer(MoveDirection direction, PlayerEntity player){

        // sets a variable for this method.
        Point location = null;

        // Looks for each Player in players if the names are equal.
        for(Player pl : players){
            if(pl.toString().equals(player.toString())){

                // Checks if player isn't Trump
                if(checkForTrump(player)){
                    return false;
                }

                // Puts pl in a new PlayerEntity and gets the location of it.
                PlayerEntity pe = (PlayerEntity) pl;
                location = pe.getLocation();

                // Checks if the player can move into the direction he wants to go.
                if(map.checkMoveDirection(location, direction)){

                    // Looks if the player is a BorderPatrol and looks if he isn't moving into Mexico.
                    if(pl.getClass().equals(BorderPatrol.class)){
                        if(location.y <= mexY){
                            return false;
                        }
                    }

                    // change the location in the Playerentity
                    if(pe.moveDirection(direction, map.getTilewidth())){
                        // Checks if the player is a Mexican and looks if he's moving into USA, so he can respawn.
                        if(pl.getClass().equals(Mexican.class)){
                            if(location.y >= usaY){
                                respawnMexican(pe);
                                mex.increaseScore();
                            }
                        }

                        // Put the player back into the list of players
                        players.set(players.indexOf(pl), respawnMexican(pe));
                        return true;
                    } else{
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Adds an obstacle
     *
     * @param location Location where the obstacle must be created.
     * @param player Will get checked if player is authorized.
     * @return If obstacle can e placed.
     */
    public boolean addObstacle(Point location, TileObject tileObject, Player player){

        // Gets the location of usa border * tileWidth and mex border * tileWidth
        // Now we can look if the tileObject isn't in USA or MEX.
        int usa = usaY * map.getTilewidth();
        int mex = (map.getHeight() - mexY) * map.getTilewidth();

        // check if the player is a trump
        if(checkForTrump(player)){
            // check if point isn't in MEX or USA
            if(location.y > usa && location.y < mex){
                // Check if the tile can be changed
                if(map.canPlaceTileObject(location)) {
                    // Change the tileObject of the tile.
                    map.changeTileObject(location, tileObject);
                    return true;
                }
            }
            else{
                return false;
            }
        }
        return false;
    }

    /**
     * Adds a player to the game.
     *
     * @param player The player that has to added to the game.
     */
    public void addPlayer(Player player){
        players.add(player);
    }

    /**
     * If a mexican gets into America or gets caught,
     * they will respawn on a selected tile.
     */
    public PlayerEntity respawnMexican(PlayerEntity pe){

        PlayerEntity playerEntity = pe;

        // Random generates an new Point when respawning
        Random rdm = new Random();
        int x = rdm.nextInt((20 - 1) + 1);
        int y = rdm.nextInt((map.getHeight() - (map.getHeight() - mexY)) + 1);

        y = map.getHeight() - y;

        // Makes the new point
        Point p = new Point(x * map.getTilewidth(), y * map.getTilewidth());

        // Check if player can move to there
        if(!map.canPlaceTileObject(p)){
            pe.respawn(p);
        }else{
            // If you cant spawn there, it will do this all again.
            pe = respawnMexican(pe);
        }

        return pe;
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
    private boolean checkForTrump(Player player) {

        for (Player p : players) {
            // Check if the player name equals to one of the players in the map.
            if(player.toString().equals(p.toString())){

                // Looks if player is instance of Trump
                return (player instanceof Trump);
            }
        }
        return false;
    }
}