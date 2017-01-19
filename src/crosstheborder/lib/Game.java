package crosstheborder.lib;

import crosstheborder.lib.computer.Computer;
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
import java.util.List;
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
    private Timer timer = new Timer();
    private boolean inProgress;

    private int scoreLimit;
    private int timeLimit;

    private Trump trump;
    private List<PlayerEntity> players = new ArrayList<>();
    private List<Computer> computers = new ArrayList<>();

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
        this.scoreLimit = settings.getScoreLimit();
        this.timeLimit = settings.getTimeLimit() * SERVER_TICK_RATE;
        this.map = MapLoader.getInstance().buildMap(mapName);
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
     * Starts the game.
     */
    public void startGame() {
        this.inProgress = true;
        this.computers.forEach(computer -> computer.resetComputer(map));

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
        if (trump == null && !user.isComputer()) {
            trump = new Trump(user.getName(), usa, settings);
            //Little hackish to set the camera location somewhere in the USA area.
            Point initCameraLoc = (Point) map.getTileInArea(usa.getTeamArea()).getLocation().clone();
            trump.getCameraLocation().setLocation(initCameraLoc);
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
            respawnPlayer(playerEntity);

            //If the user has the computer flag, add it to the computers.
            if (user.isComputer()) {
                Computer computer = new Computer(playerEntity);
                computers.add(computer);
            }
        }

//        user.setPlayer(player);
        //todo set player in gameclient
    }

    /**
     * Update is called at every tick of the game timer.
     */
    public void update(){
        //Update all the player entities.
        for (PlayerEntity player : players) {
            //Move the player if there is input.
            Point nextLocation = player.getNextMove();
            if (nextLocation != null) {
                movePlayerEntity(player, nextLocation);
            }
            //Decrease the immobilization timer.
            player.decreaseMoveTimer();
        }

        //Tick the placeables for Trump.
        trump.tickPlaceableAmount();

        //Let all the computers compute
        computers.forEach(computer -> computer.computeMove(map));

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
        Tile nextTile = map.getTile(nextLocation);

        //If the tile is accessible try to move to it.
        if (nextTile.isAccessible(player)) {

            //Boolean to store whether further evaluation of interaction/movement is needed.
            boolean shouldMove = true;

            //Check whether there's a PlayerEntity at the next location.
            if (nextTile.hasPlayerEntity()) {
                shouldMove = nextTile.getPlayerEntity().interactWith(player, this);
            }

            //Check whether there's a TileObject at the next location.
            if (shouldMove && nextTile.hasTileObject()) {
                shouldMove = nextTile.getTileObject().interactWith(player, this);
            }

            //Interact with the country of the next location.
            if (shouldMove) {
                shouldMove = nextTile.getCountry().interactWith(player, this);
            }

            //If the movement should be executed, move the player.
            if (shouldMove) {
                changePlayerEntityLocation(player, nextTile);
            }
        }
    }

    @Override
    public void respawnPlayer(PlayerEntity player) {
        //Get a free point in the team area of the player.
        Tile nextLocation = map.getFreeTileInArea(player.getTeam().getTeamArea(), player);

        //player.immobilize(settings.getRespawnTime());
        changePlayerEntityLocation(player, nextLocation);
    }

    @Override
    public void increaseScore(Team team) {
        team.increaseScore();

        System.out.printf("Mexican score = %1$d | Usa score = %2$d\n", mex.getScore(), usa.getScore()); //TODO REMOVE THIS.

        //Check whether the score limit has been reached.
        checkScore();
    }

    @Override
    public void changePlayerEntityLocation(PlayerEntity entity, Tile nextLocation) {
        Tile currentLocation = entity.getTile();

        if (currentLocation != null) {
            currentLocation.setPlayerEntity(null);
        }
        nextLocation.setPlayerEntity(entity);
    }

    @Override
    public void changeTileObjectLocation(TileObject object, Tile nextLocation) {
        Tile currentLocation = object.getTile();

        if (currentLocation != null) {
            currentLocation.setTileObject(null);
        }
        nextLocation.setTileObject(object);
    }

    @Override
    public void removeTileObject(TileObject tileObject) {
        tileObject.getTile().setTileObject(null);
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
            Tile tile = map.getTile(location);
            Placeable placeable = placeableType.getPlaceable(settings);

            if (trump.canPlace(placeable) && map.canPlacePlaceable(tile, placeable)) {
                changeTileObjectLocation(placeable, tile);
                trump.decreasePlaceableAmount(placeable);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
    }
}