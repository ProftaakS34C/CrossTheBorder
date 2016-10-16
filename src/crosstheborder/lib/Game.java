package crosstheborder.lib;

import crosstheborder.lib.enumeration.Country;
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

    private Trump trump;
    private ArrayList<PlayerEntity> players;

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
        usa = new Team(Country.USA, map.getUsaArea(), settings.getUsaScoringModifier());
        mex = new Team(Country.MEX, map.getMexicoArea(), settings.getMexicanScoringModifier());
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
        if (trump == null) {
            trump = new Trump(user.getName(), usa, settings);
            //Little hackish to set the camera location somewhere in the USA area.
            trump.getCameraLocation().setLocation(map.getFreePointInArea(usa.getTeamArea()));
            player = trump;
        } else {
            Team team;
            PlayerEntity playerEntity;
            //TODO: Make a player factory.

            //Else make a new american.
            if (usa.getTeamMembers().size() > mex.getTeamMembers().size()) {
                team = mex;
                playerEntity = new Mexican(user.getName(), team, settings);
            }

            //If there are more USA members than mexicans, make a new mexican.
            else {
                team = usa;
                playerEntity = new BorderPatrol(user.getName(), team, settings);
            }

            player = playerEntity;
            players.add(playerEntity);
            Point location = map.getFreePointInArea(team.getTeamArea());
            changePlayerEntityLocation(playerEntity, location);
        }

        user.setPlayer(player);
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

        //If the tile is accessible try to move to it.
        if (map.isAccessible(nextLocation, player)) {

            //Boolean to store whether further evaluation of interaction/movement is needed.
            boolean shouldMove = true;

            //Check whether there's a tileObject at the next location.
            if (map.hasPlayerEntity(nextLocation)) {
                shouldMove = map.getPlayerEntity(nextLocation).interactWith(player, this);
            }

            //Check whether there's a PlayerEntity at the next location.
            if (map.hasTileObject(nextLocation) && shouldMove) {
                shouldMove = map.getTileObject(nextLocation).interactWith(player, this);
            }

            //Interact with the country of the next location.
            if (shouldMove) {
                shouldMove = map.getCountry(nextLocation).interactWith(player, this);
            }

            //If the movement should be executed, move the player.
            if (shouldMove) {
                changePlayerEntityLocation(player, nextLocation);
            }
        }
    }

    @Override
    public void respawnPlayer(PlayerEntity player) {
        //Get a free point in the team area of the player.
        Point nextLocation = map.getFreePointInArea(player.getTeam().getTeamArea());

        player.immobilize(settings.getRespawnTime());
        changePlayerEntityLocation(player, nextLocation);
    }

    @Override
    public void increaseScore(Team team) {
        team.increaseScore();
    }

    @Override
    public void changePlayerEntityLocation(PlayerEntity entity, Point nextLocation) {
        Point currentLocation = entity.getLocation();

        map.changePlayerEntity(currentLocation, null);
        map.changePlayerEntity(nextLocation, entity);
        //Move the location of the entity to the next location. Saves having to recreate a new point object every time.
        currentLocation.move(nextLocation.x, nextLocation.y);
    }

    @Override
    public void changeTileObjectLocation(TileObject object, Point nextLocation) {
        Point currentLocation = object.getLocation();

        map.changeTileObject(currentLocation, null);
        map.changeTileObject(nextLocation, object);
        //Move the location of the entity to the next location. Saves having to recreate a new point object every time.
        currentLocation.move(nextLocation.x, nextLocation.y);
    }

    @Override
    public void removeTileObject(TileObject tileObject) {
        map.changeTileObject(tileObject.getLocation(), null);
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

            if (this.trump.canPlace(placeable) && map.canPlacePlaceable(location, placeable)) {
                map.changeTileObject(location, placeable);
                this.trump.decreasePlaceableAmount(placeable);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
    }
}