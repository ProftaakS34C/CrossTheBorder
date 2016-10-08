package crosstheborder.lib;

import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.player.Trump;

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
     * Moves a {@link PlayerEntity} according to the next input from it's {@link InputBuffer}.
     *
     * @param player The {@link PlayerEntity} that should be moved.
     */
    public void movePlayer(PlayerEntity player) {
        //TODO PlayerEntity should be checked whether it can move.
        MoveDirection moveDirection = player.getInputBuffer().getNextInputMove();

        //Exit the method as quickly as possible when there should be no movement.
        if (moveDirection == MoveDirection.NONE) {
            return;
        }

        Point currentLocation = player.getLocation();
        Point translation = moveDirection.getTranslation();
        Point nextLocation = new Point(currentLocation.x + translation.x, currentLocation.y + translation.y);

        //Check whether there's a tileObject at the next location.
        if (map.hasTileObject(nextLocation)) {
            player.interactWith(map.getTileObject(nextLocation));
            //TODO let interactWith return a boolean that indicates whether it has called InteractionHandler.
        }

        //Move the player to the next location if possible.
        if (map.isAccessible(nextLocation)) {
            map.changeTileObject(currentLocation, null);
            map.changeTileObject(nextLocation, player);
            //Translates the location of the playerEntity. Saves having to recreate a new point object every time.
            currentLocation.translate(translation.x, translation.y);
        }
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