package crosstheborder.lib;

import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.player.Trump;
import crosstheborder.lib.tileobject.Placeable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class makes an instance of Game
 *
 * @author guillaime
 * @author Oscar de Leeuw
 */
public class Game implements GameManipulator {

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

        // For now we use 20 as width and height, this can be changed if we want to.
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

    @Override
    public void movePlayerEntity(PlayerEntity player, Point nextLocation) {
        Point currentLocation = player.getLocation();

        //Check whether there's a tileObject at the next location.
        if (map.hasTileObject(nextLocation)) {
            map.getTileObject(nextLocation).interactWith(player, this);
        }
        //If there is no tileObject move the player.
        else {
            map.changeTileObject(currentLocation, null);
            map.changeTileObject(nextLocation, player);
            //Move the location of the entity to the next location. Saves having to recreate a new point object every time.
            currentLocation.move(nextLocation.x, nextLocation.y);
        }
    }


    public void addObstacle(Point location, Placeable tileObject) {

        if (!map.hasTileObject(location) || map.getMexicoArea().contains(location) || map.getUsaArea().contains(location)) {
            //TODO add code that checks walls are not placed next to each other.
            map.changeTileObject(location, tileObject);
        }
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
     * Update is called every at tick of the server clock.
     */
    public void update(){
        //Update all the players.
        for (Player player : players) {
            //Update the player entities.
            if (player instanceof PlayerEntity) {
                PlayerEntity entity = (PlayerEntity) player;
                entity.decreaseMoveTimer();

                Point nextLocation = entity.getNextMove();
                if (nextLocation != null) {
                    movePlayerEntity(entity, nextLocation);
                }
            }
        }
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

    @Override
    public void respawnPlayer(PlayerEntity player) {

    }

    @Override
    public void increaseScore(Team team, int amount) {

    }

    @Override
    public void changeTileObjectLocation(TileObject tileObject, Point newLocation) {

    }
}