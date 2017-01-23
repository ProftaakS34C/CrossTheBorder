package crosstheborder.lib;

import crosstheborder.lib.enumeration.Country;
import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.enumeration.TileType;
import crosstheborder.lib.player.PlayerEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by guill on 22-1-2017.
 */
public class GameTest {

    private Game game;
    private final static String MAPNAME = "mainmap";

    private List<User> users = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        game = new Game(MAPNAME);

        users.add(new User("Henk"));
        users.add(new User("Arie"));
        users.add(new User("Ans"));
        users.add(new User("Jantje"));
        users.add(new User("Pietje"));
    }

    /**
     * @throws Exception
     *
     * Before we can test if players can walk in the game we have to add some players to the game.
     * We've already made some user in setUp to add to the game.
     *
     * After we've added some players we can start the game and look if the player at index 1 is a Mexican.
     * Then we get the location of the mexican and change it to 1 to the left, right, up and down.
     * If the location changes, they can walk.
     */
    @Test
    public void movePlayerEntity() throws Exception {

        // Adding the players into the game
        for (User u:users) { game.addPlayer(u); }

        Assert.assertEquals(users.get(0).getName(), game.getTrump().getName());
        Assert.assertEquals(users.size() - 1, game.getPlayers().size());

        // We start the game
        game.startGame();

        // Look if player @ index 1 is a Mexican
        Assert.assertEquals(users.get(1).getName(), game.getPlayers().get(0).getName());
        Assert.assertEquals(Country.MEX, game.getPlayers().get(0).getTeam().getCountry());

        // We get the PlayerEntity of the Mexican and location
        PlayerEntity playerEntity = game.getPlayers().get(0);
        Point first_location = playerEntity.getLocation();

        // Move player to the left and check new location
        game.sendMoveInput(MoveDirection.LEFT, playerEntity);
        game.update();

        Assert.assertEquals(new Point(first_location.x - 1, first_location.y), game.getPlayers().get(0).getLocation());

        // Move player one tile up and check location
        game.sendMoveInput(MoveDirection.UP, playerEntity);
        game.update();

        Assert.assertEquals(new Point(first_location.x -1, first_location.y -1), game.getPlayers().get(0).getLocation());

        // Move player one tile to the right and check location
        game.sendMoveInput(MoveDirection.RIGHT, playerEntity);
        game.update();

        Assert.assertEquals(new Point(first_location.x, first_location.y -1), game.getPlayers().get(0).getLocation());

        // Move player one tile down and check location
        game.sendMoveInput(MoveDirection.DOWN, playerEntity);
        game.update();

        Assert.assertEquals(new Point(first_location.x, first_location.y), game.getPlayers().get(0).getLocation());
    }

    /**
     * @throws Exception
     *
     * A player can only respawn if the player is an Mexican that gets caught by the BorderPatrol or gets across the border.
     * First we have to make a few players and look where the border is on the map.
     * Choose a tile where the mexican can stand on so he can move to the border.
     * Move the Mexican towards the border en check if he respawns and the teams gets a point.
     *
     * We also need to check if the player can respawn when the BorderPatrol will catch the Mexican.
     * We have to put a BorderPatrol next to an Mexican. Move the BorderPatrol to the mexican and check the scores an locations.
     */
    @Test
    public void respawnPlayer() throws Exception {

        // Adding the players into the game
        for (User u:users) { game.addPlayer(u); }

        // We start the game
        game.startGame();

        // We get the PlayerEntity of the Mexican and location
        PlayerEntity mexican = game.getPlayers().get(0);

        // Point that is exactly 1 tile under the border
        Point point_under_border = new Point(16,12);
        Tile tile_under_border = game.getMap().getTile(point_under_border);

        // Change player location to the selected tile and check
        game.changePlayerEntityLocation(mexican, tile_under_border);
        game.update();

        Assert.assertEquals(point_under_border, game.getPlayers().get(0).getLocation());

        // Move player 1 tile up and check respawn
        game.sendMoveInput(MoveDirection.UP, mexican);
        game.update();

        Assert.assertEquals(1, game.getMexico().getScore());
        Assert.assertNotEquals(new Point(point_under_border.x - 1, point_under_border.y), game.getPlayers().get(0).getLocation());

        // Get the BorderPatrol from the game and the tile to stand on later on
        PlayerEntity borderPatrol = game.getPlayers().get(1);
        Point point_under_border_bp = new Point(17, 12);
        Tile tile_under_border_bp = game.getMap().getTile(point_under_border_bp);

        // Move both PlayerEntities next to each other
        game.changePlayerEntityLocation(mexican, tile_under_border);
        game.changePlayerEntityLocation(borderPatrol, tile_under_border_bp);

        // Move the BorderPatrol to the Mexican
        game.sendMoveInput(MoveDirection.LEFT, borderPatrol);
        game.update();

        Assert.assertEquals(1, game.getUsa().getScore());
        Assert.assertEquals(1, game.getMexico().getScore());
    }
}