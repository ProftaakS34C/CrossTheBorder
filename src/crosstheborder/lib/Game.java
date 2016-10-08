package crosstheborder.lib;

import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.tileobject.Placeable;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class makes an instance of Game.
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
        players = new ArrayList<>();

        // For now we use 20 as width and height, this can be changed if we want to.
        map = new Map("The Border", 40, 40); //TODO Fuck the magic numbers and implement map construction..
        usa = new Team("USA", map.getUsaArea());
        mex = new Team("MEX", map.getMexicoArea());
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
     * Adds a player to the game.
     *
     * @param player The player that has to added to the game.
     */
    public void addPlayer(Player player){
        players.add(player);
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

    public void addObstacle(Point location, Placeable tileObject) {

        if (!map.hasTileObject(location) || map.getMexicoArea().contains(location) || map.getUsaArea().contains(location)) {
            //TODO add code that checks walls are not placed next to each other.
            map.changeTileObject(location, tileObject);
        }
    }

    @Override
    public void movePlayerEntity(PlayerEntity player, Point nextLocation) {
        //Check whether there's a tileObject at the next location.
        if (map.hasTileObject(nextLocation)) {
            map.getTileObject(nextLocation).interactWith(player, this);
        }
        //If there is no tileObject move the player.
        else {
            changeTileObjectLocation(player, nextLocation);
        }
    }

    @Override
    public void respawnPlayer(PlayerEntity player) {
        Rectangle area = player.getTeam().getTeamArea();

        //Generate a random point within the teams area.
        int x = ThreadLocalRandom.current().nextInt(area.x, area.x + area.width + 1);
        int y = ThreadLocalRandom.current().nextInt(area.y, area.y + area.height + 1);
        Point nextLocation = new Point(x, y);

        //If the tile is occupied find a new location.
        if (map.hasTileObject(nextLocation)) {
            respawnPlayer(player);
        } else {
            player.setCanMoveTicks(10); //TODO Gather the respawn time from game settings.
            changeTileObjectLocation(player, nextLocation);
        }
    }

    @Override
    public void increaseScore(Team team, int amount) {
        team.increaseScore(amount);
    }

    @Override
    public void changeTileObjectLocation(TileObject tileObject, Point nextLocation) {
        Point currentLocation = tileObject.getLocation();

        map.changeTileObject(currentLocation, null);
        map.changeTileObject(nextLocation, tileObject);
        //Move the location of the entity to the next location. Saves having to recreate a new point object every time.
        currentLocation.move(nextLocation.x, nextLocation.y);
    }
}