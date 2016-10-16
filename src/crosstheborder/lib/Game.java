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
import java.util.Timer;
import java.util.TimerTask;
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
    private static final int SERVER_TICK_RATE = ServerSettings.getInstance().getServerTickRate();
    private GameSettings settings;
    private Timer timer;
    private boolean inProgress;

    private int scoreLimit;
    private int timeLimit;

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
        this.settings = new GameSettingsImpl(SERVER_TICK_RATE);
        this.timer = new Timer();
        this.scoreLimit = settings.getScoreLimit();
        this.timeLimit = settings.getTimeLimit() * SERVER_TICK_RATE;
        this.players = new ArrayList<>();
        this.map = MapLoader.getInstance().buildMap(mapName);
        this.usa = new Team("USA", map.getUsaArea(), settings.getUsaScoringModifier());
        this.mex = new Team("MEX", map.getMexicoArea(), settings.getMexicanScoringModifier());
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
     * Starts the game.
     */
    public void startGame() {
        this.inProgress = true;

        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 1000 / SERVER_TICK_RATE);
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

        //Check whether time has expired.
        timeLimit--;
        checkTime();
    }

    /**
     * Checks whether the score limit has been reached.
     */
    private void checkScore() {
        if (mex.getScore() >= scoreLimit || usa.getScore() >= scoreLimit) {
            stop();
        }
    }

    /**
     * Checks whether the time limit has been reached.
     */
    private void checkTime() {
        if (timeLimit <= 0) {
            stop();
        }
    }

    /**
     * Stops the game.
     */
    public void stop() {
        timer.cancel();
        this.inProgress = false;
    }

    @Override
    public void movePlayerEntity(PlayerEntity player, Point nextLocation) {
        boolean canContinue = true;

        //Check whether there's a PlayerEntity at the next location.
        if (canContinue && map.hasTileObject(nextLocation)) {
            canContinue = map.getTileObject(nextLocation).interactWith(player, this);
        }
        //Check whether the movement would enter a country.
        if (canContinue && map.hasCountry(nextLocation)) {
            canContinue = player.interactWith(map.getCountry(nextLocation), this);
        }
        //Check whether there's a tileObject at the next location.
        if (canContinue && map.hasPlayerEntity(nextLocation)) {
            canContinue = map.getPlayerEntity(nextLocation).interactWith(player, this);
        }
        //If the tile is free just move the entity.
        if (canContinue) {
            changePlayerEntityLocation(player, nextLocation);
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

        //Check whether the score limit has been reached.
        checkScore();
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