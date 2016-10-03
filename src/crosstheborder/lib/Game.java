package crosstheborder.lib;

import crosstheborder.lib.enums.MoveDirection;
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
    private int usaY;
    private int mexY;

    /**
     * Constructor of Game class.
     */
    public Game(){
        usa = new Team("USA");
        mex = new Team("MEX");
        players = new ArrayList<>();
        map = new Map("The Border", 20, 20);
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

        Point location = null;

        for(Player pl : players){
            if(pl.toString().equals(player.toString())){
                PlayerEntity pe = (PlayerEntity) pl;
                location = pe.getLocation();

                if(map.checkMoveDirection(location, direction)){

                    if(pl.getClass().equals(BorderPatrol.class)){
                        if(location.y <= mexY){
                            return false;
                        }
                    }

                    if(pe.moveDirection(direction)){
                        if(pl.getClass().equals(Mexican.class)){
                            if(location.y >= usaY){
                                respawnMexican(pe);
                                mex.increaseScore();
                            }
                        }

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

        int usa = usaY * map.getTilewidth();
        int mex = (map.getHeight() - mexY) * map.getTilewidth();

        if(checkForTrump(player)){
            if(location.y > usa && location.y < mex){
                if(map.canPlaceTileObject(location)) {
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

        Random rdm = new Random();
        int x = rdm.nextInt((20 - 1) + 1);
        int y = rdm.nextInt((map.getHeight() - (map.getHeight() - mexY)) + 1);

        y = map.getHeight() - y;

        Point p = new Point(x * map.getTilewidth(), y * map.getTilewidth());

        if(!map.canPlaceTileObject(p)){
            pe.respawn(p);
        }else{
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

            if(player.toString().equals(p.toString())){

                if (player instanceof Trump) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }
}