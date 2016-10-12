package crosstheborder.lib;

import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.enumeration.PlaceableType;
import crosstheborder.lib.interfaces.*;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.player.Trump;
import crosstheborder.lib.player.entity.BorderPatrol;
import crosstheborder.lib.player.entity.Mexican;
import crosstheborder.lib.tileobject.Placeable;

import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class makes an instance of Game.
 *
 * @author guillaime
 * @author Oscar de Leeuw
 */
public class Game implements GameManipulator, GameInterface {
    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());
    private GameSettings settings;

    private ArrayList<Player> players;
    private Map map;
    private Team usa;
    private Team mex;

    /**
     * Constructor of Game class.
     *
     * @param mapName The name of the map.
     */
    public Game(String mapName) {
        players = new ArrayList<>();
        settings = new GameSettingsImpl(ServerSettings.getInstance().getServerTickRate());

        map = MapLoader.getInstance().buildMap(mapName);
        usa = new Team("USA", map.getUsaArea());
        mex = new Team("MEX", map.getMexicoArea());
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

        player.immobilize(settings.getRespawnTime());
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

    @Override
    public Camera getCamera(Point center, int tileWidth, int cameraWidth, int cameraHeight) {
        return map.getCamera(center, tileWidth, cameraWidth, cameraHeight);
    }

    @Override
    public Team getUsa() {
        return this.usa;
    }

    @Override
    public Team getMexico() {
        return this.mex;
    }

    @Override
    public int getRemainingTime() {
        throw new UnsupportedOperationException(); //TODO add time limit to the game.
    }

    @Override
    public void sendMoveInput(MoveDirection md, PlayerEntity player) {
        if (players.contains(player)) {
            player.pushInput(md);
        }
    }

    @Override
    public void addPlaceable(Point location, PlaceableType placeableType) {
        try {
            Placeable placeable = placeableType.getPlaceable(settings);
            Trump trump = getTrump();

            if (trump.canPlace(placeable) && map.canPlacePlaceable(location, placeable)) {
                map.changeTileObject(location, placeable);
                trump.decreasePlaceableAmount(placeable);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
    }
}