package crosstheborder.lib;

import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.enumeration.ObstacleType;
import crosstheborder.lib.enumeration.PlaceableType;
import crosstheborder.lib.interfaces.GameSettings;
import crosstheborder.lib.player.Trump;
import crosstheborder.lib.player.entity.BorderPatrol;
import crosstheborder.lib.player.entity.Mexican;
import crosstheborder.lib.tileobject.Obstacle;
import crosstheborder.lib.tileobject.placeable.Wall;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * @author Oscar de Leeuw
 */
public class GameTest {
    private static final int TICK_RATE = ServerSettings.getInstance().getServerTickRate();

    private Game game;
    private GameSettings settings;

    private User user1 = new User("Trump");
    private User user2 = new User("Juan");
    private User user3 = new User("Pete");

    private Trump trump;
    private Mexican mexican;
    private BorderPatrol borderPatrol;


    @Before
    public void setUp() throws Exception {
        game = new Game("empty");
        settings = game.getSettings();

        game.addPlayer(user1);
        game.addPlayer(user2);
        game.addPlayer(user3);

        trump = (Trump) user1.getPlayer();
        mexican = (Mexican) user2.getPlayer();
        borderPatrol = (BorderPatrol) user3.getPlayer();

        //Remove the respawn immobilization.
        for (int i = 0; i < settings.getServerTickRate() * settings.getRespawnTime(); i++) {
            game.update();
        }
    }

    @Test
    public void addPlayer() throws Exception {
        assertTrue(user1.getPlayer() instanceof Trump);
        assertTrue(user2.getPlayer() instanceof Mexican);
        assertTrue(user3.getPlayer() instanceof BorderPatrol);

        //Check if the mexican and borderpatrol location is within their zone.
        assertTrue(mexican.getTeam().getTeamArea().contains(mexican.getLocation()));
        assertTrue(borderPatrol.getTeam().getTeamArea().contains(borderPatrol.getLocation()));

        //Check if the mexican and borderpatrol exist on the map.
        assertEquals(game.getMap().getTile(mexican.getLocation()).getPlayerEntity(), mexican);
        assertEquals(game.getMap().getTile(borderPatrol.getLocation()).getPlayerEntity(), borderPatrol);

        //Check whether the teams contain the correct team members.
        assertTrue(game.getUsa().getTeamMembers().contains(borderPatrol));
        assertTrue(game.getUsa().getTeamMembers().contains(trump));
        assertTrue(game.getMexico().getTeamMembers().contains(mexican));
    }

    @Test
    public void update() throws Exception {
        Point initialBPLocation = new Point(borderPatrol.getLocation().x, borderPatrol.getLocation().y);
        Point initialMexLocation = new Point(mexican.getLocation().x, mexican.getLocation().y);

        //Immobilize the mexican.
        mexican.immobilize(1);

        //Process the amount of ticks that equals 1 second, which should allow the mexican to move again.
        for (int i = 0; i < 1 * TICK_RATE; i++) {
            //Push movement. up to mexican and down to border patrol
            game.sendMoveInput(MoveDirection.UP, mexican);
            game.sendMoveInput(MoveDirection.DOWN, borderPatrol);

            game.update();
            assertEquals(initialMexLocation.x, mexican.getLocation().x);
            assertEquals(initialMexLocation.y, mexican.getLocation().y);

            initialBPLocation.translate(0, 1);
            assertEquals(initialBPLocation.x, borderPatrol.getLocation().x);
            assertEquals(initialBPLocation.y, borderPatrol.getLocation().y);
        }

        //Push one more up movement to the mexican object.
        game.sendMoveInput(MoveDirection.UP, mexican);

        //update one more time which should move the mexican.
        game.update();
        assertEquals(initialMexLocation.x, mexican.getLocation().x);
        assertEquals(initialMexLocation.y - 1, mexican.getLocation().y);
    }

    @Test
    public void addPlaceable() throws Exception {
        Point location = new Point(20, 20);
        Point location2 = new Point(22, 20);
        Point location3 = new Point(21, 20);
        Point location4 = new Point(21, 21);

        int wallCount = trump.getWallAmount();
        int trapCount = trump.getTrapAmount();

        //Add a placeable at a (free) location.
        //Should succeed.
        game.addPlaceable(location, PlaceableType.WALL);
        assertEquals(wallCount - 1, trump.getWallAmount());
        //Should succeed.
        game.addPlaceable(location2, PlaceableType.WALL);
        assertEquals(wallCount - 2, trump.getWallAmount());
        //Should succeed.
        game.addPlaceable(location3, PlaceableType.WALL);
        assertEquals(wallCount - 3, trump.getWallAmount());
        //Should fail.
        game.addPlaceable(location4, PlaceableType.WALL);
        assertEquals(wallCount - 3, trump.getWallAmount());

        assertTrue(game.getMap().getTile(location).getTileObject() instanceof Wall);
        assertTrue(game.getMap().getTile(location2).getTileObject() instanceof Wall);
        assertTrue(game.getMap().getTile(location3).getTileObject() instanceof Wall);
        assertNull(game.getMap().getTile(location4).getTileObject());


        game.addPlaceable(location2, PlaceableType.WALL);

        game.addPlaceable(location3, PlaceableType.WALL);

        //Try to add a placeable at the same location.
        game.addPlaceable(location, PlaceableType.TRAP);
        assertTrue(game.getMap().getTile(location).getTileObject() instanceof Wall);
        assertEquals(trapCount, trump.getTrapAmount());
    }

    @Test
    public void movePlayerEntity() throws Exception {
        Point playerLocation = mexican.getLocation();
        Point oldLocation = new Point(playerLocation.x, playerLocation.y);
        Point nextLocation1 = new Point(playerLocation.x + 1, playerLocation.y);
        Tile nextLocation2 = game.getMap().getTile(playerLocation.x + 1, playerLocation.y - 1);

        //Move the player to an empty tile.
        game.movePlayerEntity(mexican, nextLocation1);

        assertEquals(mexican, game.getMap().getTile(nextLocation1).getPlayerEntity());
        assertFalse(game.getMap().getTile(oldLocation).hasTileObject());
        assertEquals(mexican.getLocation().x, nextLocation1.x);
        assertEquals(mexican.getLocation().y, nextLocation1.y);

        //Set an obstacle at the location the mexican wants to move to.
        Obstacle obstacle = new Obstacle(ObstacleType.TREE);
        game.changeTileObjectLocation(obstacle, nextLocation2);

        assertEquals(obstacle, nextLocation2.getTileObject());

        game.movePlayerEntity(mexican, nextLocation2.getLocation());

        //Mexican should not have moved.
        assertEquals(mexican, game.getMap().getTile(mexican.getLocation()).getPlayerEntity());
        assertEquals(mexican, game.getMap().getTile(nextLocation1).getPlayerEntity());
        assertEquals(obstacle, nextLocation2.getTileObject());
    }

    @Test
    public void respawnPlayer() throws Exception {
        Point oldLocation = new Point(mexican.getLocation().x, mexican.getLocation().y);

        game.respawnPlayer(mexican);

        assertFalse(game.getMap().getTile(oldLocation).hasTileObject());
        assertTrue(mexican.getTeam().getTeamArea().contains(mexican.getLocation()));
    }

    @Test
    public void increaseScore() throws Exception {
        int initUsaScore = game.getUsa().getScore();
        int initMexScore = game.getUsa().getScore();

        game.increaseScore(borderPatrol.getTeam());
        game.increaseScore(mexican.getTeam());

        assertEquals(initUsaScore + 1, game.getUsa().getScore());
        assertEquals(initMexScore + 1, game.getMexico().getScore());
    }

    @Test
    public void changeTileObjectLocation() throws Exception {
        Tile tile = game.getMap().getTile(10, 10);
        Obstacle obstacle = new Obstacle(ObstacleType.TREE);

        //Set a new tile object on the map.
        game.changeTileObjectLocation(obstacle, tile);
        assertEquals(obstacle, tile.getTileObject());
        assertEquals(obstacle.getLocation().x, tile.getLocation().x);
        assertEquals(obstacle.getLocation().y, tile.getLocation().y);

        //Move a tile object.
        Point playerLocation = mexican.getLocation();
        Point oldLocation = new Point(playerLocation.x, playerLocation.y);
        Tile nextTile = game.getMap().getTile(playerLocation.x + 1, playerLocation.y);

        game.changePlayerEntityLocation(mexican, nextTile);

        assertEquals(mexican, nextTile.getPlayerEntity());
        assertFalse(game.getMap().getTile(oldLocation).hasTileObject());
        assertEquals(mexican.getLocation().x, nextTile.getLocation().x);
        assertEquals(mexican.getLocation().y, nextTile.getLocation().y);

    }

}