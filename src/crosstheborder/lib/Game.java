package crosstheborder.lib;

import crosstheborder.lib.ability.Crawler;
import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.player.Trump;
import crosstheborder.lib.player.entity.BorderPatrol;
import crosstheborder.lib.player.entity.Mexican;
import crosstheborder.lib.tileobject.Placeable;

import java.awt.*;
import java.util.ArrayList;

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
     * Turns a user into a player and adds the player to the game.
     * Will try to create a Trump if there is none.
     * Will try to create a Mexican if there are more Americans than Mexicans.
     * Will otherwise try to create a BorderPatrol.
     *
     * @param user The user that joined this game.
     */
    public void addPlayer(User user) {
        Player player;

        //If trump does not exist make the user a trump.
        if (!players.stream().anyMatch(x -> x instanceof Trump)) {
            player = new Trump(user.getName(), usa);
        }
        //If there are more USA members than mexicans, make a new mexican.
        else if (usa.getTeamMembers().size() > mex.getTeamMembers().size()) {
            Ability ability = new Crawler(0); //TODO something with abilities.
            Point location = map.getFreePointInArea(mex.getTeamArea());

            player = new Mexican(user.getName(), location, mex, ability);
            changeTileObjectLocation((Mexican) player, location);
        }
        //Else make a new american.
        else {
            Point location = map.getFreePointInArea(usa.getTeamArea());

            player = new BorderPatrol(user.getName(), location, usa);
            changeTileObjectLocation((BorderPatrol) player, location);
        }

        //Assign the player object to the user.
        user.setPlayer(player);
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
        //Get a free point in the team area of the player.
        Point nextLocation = map.getFreePointInArea(player.getTeam().getTeamArea());

        player.immobilize(10); //TODO Gather the respawn time from game settings.
        changeTileObjectLocation(player, nextLocation);
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