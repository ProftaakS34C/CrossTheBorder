package crosstheborder.lib;

import crosstheborder.lib.enumeration.TeamName;
import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.GameSettings;
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
    private GameSettings settings;

    private ArrayList<Player> players;
    private Map map;
    private Team usa;
    private Team mex;

    /**
     * Constructor of Game class.
     */
    public Game(String mapName) {
        players = new ArrayList<>();
        settings = new GameSettingsImpl(ServerSettings.getInstance().getServerTickRate());

        map = MapLoader.getInstance().buildMap(mapName);
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
     * Gets the map of the game.
     *
     * @return The map that is used by the game.
     */
    public Map getMap() {
        return this.map;
    }

    /**
     * Gets the game settings for this game.
     *
     * @return The settings of this game.
     */
    public GameSettings getSettings() {
        return this.settings;
    }

    /**
     * Gets a team from the game with a given team name.
     *
     * @param name The name of the team.
     * @return A team object that represents the team.
     */
    public Team getTeam(TeamName name) {
        switch (name) {
            case USA:
                return this.usa;
            case MEX:
                return this.mex;
            default:
                return null;
        }
    }

    /**
     * Gets the Trump in the list of players.
     * @return The Trump object.
     */
    private Trump getTrump() {
        for (Player player : players) {
            if (player instanceof Trump) {
                return (Trump) player;
            }
        }
        return null;
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
            player = new Trump(user.getName(), usa, settings);
        }
        //If there are more USA members than mexicans, make a new mexican.
        else if (usa.getTeamMembers().size() > mex.getTeamMembers().size()) {
            Point location = map.getFreePointInArea(mex.getTeamArea());

            player = new Mexican(user.getName(), mex, settings);
            changeTileObjectLocation((Mexican) player, location);
        }
        //Else make a new american.
        else {
            Point location = map.getFreePointInArea(usa.getTeamArea());

            player = new BorderPatrol(user.getName(), usa, settings);
            changeTileObjectLocation((BorderPatrol) player, location);
        }

        //Assign the player object to the user.
        user.setPlayer(player);
        players.add(player);
    }

    /**
     * Update is called at every tick of the server clock.
     */
    public void update(){
        //Update all the players.
        for (Player player : players) {
            //Update the player entities.
            if (player instanceof PlayerEntity) {
                PlayerEntity entity = (PlayerEntity) player;

                //Move the player if there is input.
                Point nextLocation = entity.getNextMove();
                if (nextLocation != null) {
                    movePlayerEntity(entity, nextLocation);
                }

                //Decrease the immobilization timer.
                entity.decreaseMoveTimer();
            } else if (player instanceof Trump) {
                ((Trump) player).tickPlaceableAmount();
            }
        }
    }

    /**
     * Adds a new Placeable to the map.
     *
     * @param location  The location the placeable should be placed.
     * @param placeable The placeable that should be placed on the map.
     */
    public void addPlaceable(Point location, Placeable placeable) {
        Trump trump = getTrump();

        if (trump.canPlace(placeable) && map.canPlacePlaceable(location, placeable)) {
            map.changeTileObject(location, placeable);
            trump.decreasePlaceableAmount(placeable);
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