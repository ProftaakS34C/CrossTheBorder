package crosstheborder.lib;

import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.enumeration.ObstacleType;
import crosstheborder.lib.enumeration.TeamName;
import crosstheborder.lib.player.Trump;
import crosstheborder.lib.player.entity.BorderPatrol;
import crosstheborder.lib.player.entity.Mexican;
import crosstheborder.lib.tileobject.Obstacle;
import crosstheborder.lib.tileobject.placeable.Trap;
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

    private User user1 = new User("Trump");
    private User user2 = new User("Juan");
    private User user3 = new User("Pete");

    private Trump trump;
    private Mexican mexican;
    private BorderPatrol borderPatrol;


    @Before
    public void setUp() throws Exception {
        game = new Game("empty");

        game.addPlayer(user1);
        game.addPlayer(user2);
        game.addPlayer(user3);

        trump = (Trump) user1.getPlayer();
        mexican = (Mexican) user2.getPlayer();
        borderPatrol = (BorderPatrol) user3.getPlayer();
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
        assertEquals(game.getMap().getTileObject(mexican.getLocation()), mexican);
        assertEquals(game.getMap().getTileObject(borderPatrol.getLocation()), borderPatrol);

        //Check whether the teams contain the correct team members.
        assertTrue(game.getTeam(TeamName.USA).getTeamMembers().contains(borderPatrol));
        assertTrue(game.getTeam(TeamName.USA).getTeamMembers().contains(trump));
        assertTrue(game.getTeam(TeamName.MEX).getTeamMembers().contains(mexican));
    }

    @Test
    public void update() throws Exception {
        Point initialBPLocation = new Point(borderPatrol.getLocation().x, borderPatrol.getLocation().y);
        Point initialMexLocation = new Point(mexican.getLocation().x, mexican.getLocation().y);

        //Immobilize the mexican.
        mexican.immobilize(1);

        //Process the amount of ticks that equals 1 second, which should allow the mexican to move again.
        for (int i = 0; i < 1 * TICK_RATE; i++) {
            //Push movement. up to mexico and down to border patrol
            mexican.pushInput(MoveDirection.UP);
            borderPatrol.pushInput(MoveDirection.DOWN);

            game.update();
            assertEquals(initialMexLocation.x, mexican.getLocation().x);
            assertEquals(initialMexLocation.y, mexican.getLocation().y);

            initialBPLocation.translate(0, 1);
            assertEquals(initialBPLocation.x, borderPatrol.getLocation().x);
            assertEquals(initialBPLocation.y, borderPatrol.getLocation().y);
        }

        //Push one more up movement to the mexican object.
        mexican.pushInput(MoveDirection.UP);

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

        Wall wall = new Wall(game.getSettings());
        Wall wall2 = new Wall(game.getSettings());
        Wall wall3 = new Wall(game.getSettings());
        Wall wall4 = new Wall(game.getSettings());
        Trap trap = new Trap(game.getSettings());
        int wallCount = trump.getWallAmount();
        int trapCount = trump.getTrapAmount();

        //Add a placeable at a (free) location.
        //Should succeed.
        game.addPlaceable(location, wall);
        assertEquals(wallCount - 1, trump.getWallAmount());
        //Should succeed.
        game.addPlaceable(location2, wall2);
        assertEquals(wallCount - 2, trump.getWallAmount());
        //Should succeed.
        game.addPlaceable(location3, wall3);
        assertEquals(wallCount - 3, trump.getWallAmount());
        //Should fail.
        game.addPlaceable(location4, wall4);
        assertEquals(wallCount - 3, trump.getWallAmount());

        assertEquals(game.getMap().getTileObject(location), wall);
        assertEquals(game.getMap().getTileObject(location2), wall2);
        assertEquals(game.getMap().getTileObject(location3), wall3);
        assertNull(game.getMap().getTileObject(location4));


        game.addPlaceable(location2, wall2);

        game.addPlaceable(location3, wall3);

        //Try to add a placeable at the same location.
        game.addPlaceable(location, trap);
        assertEquals(game.getMap().getTileObject(location), wall);
        assertEquals(trapCount, trump.getTrapAmount());
    }

    @Test
    public void movePlayerEntity() throws Exception {
        Point playerLocation = mexican.getLocation();
        Point oldLocation = new Point(playerLocation.x, playerLocation.y);
        Point nextLocation1 = new Point(playerLocation.x + 1, playerLocation.y);
        Point nextLocation2 = new Point(playerLocation.x + 1, playerLocation.y - 1);

        //Move the player to an empty tile.
        game.movePlayerEntity(mexican, nextLocation1);

        assertEquals(mexican, game.getMap().getTileObject(nextLocation1));
        assertFalse(game.getMap().hasTileObject(oldLocation));
        assertEquals(mexican.getLocation().x, nextLocation1.x);
        assertEquals(mexican.getLocation().y, nextLocation1.y);

        //Set an obstacle at the location the mexican wants to move to.
        Obstacle obstacle = new Obstacle(ObstacleType.TREE);
        game.changeTileObjectLocation(obstacle, nextLocation2);

        assertEquals(obstacle, game.getMap().getTileObject(nextLocation2));

        game.movePlayerEntity(mexican, nextLocation2);

        //Mexican should not have moved.
        assertEquals(mexican, game.getMap().getTileObject(mexican.getLocation()));
        assertEquals(mexican, game.getMap().getTileObject(nextLocation1));
        assertEquals(obstacle, game.getMap().getTileObject(nextLocation2));
    }

    @Test
    public void respawnPlayer() throws Exception {
        Point oldLocation = new Point(mexican.getLocation().x, mexican.getLocation().y);

        game.respawnPlayer(mexican);

        assertFalse(game.getMap().hasTileObject(oldLocation));
        assertTrue(mexican.getTeam().getTeamArea().contains(mexican.getLocation()));
    }

    @Test
    public void increaseScore() throws Exception {
        int initUsaScore = game.getScoreUSA();
        int initMexScore = game.getScoreMexico();
        int amount1 = 1;
        int amount2 = 3;

        game.increaseScore(borderPatrol.getTeam(), amount1);
        game.increaseScore(mexican.getTeam(), amount2);

        assertEquals(initUsaScore + amount1, game.getScoreUSA());
        assertEquals(initMexScore + amount2, game.getScoreMexico());
    }

    @Test
    public void changeTileObjectLocation() throws Exception {
        Point location = new Point(10, 10);
        Obstacle obstacle = new Obstacle(ObstacleType.TREE);

        //Set a new tile object on the map.
        game.changeTileObjectLocation(obstacle, location);
        assertEquals(obstacle, game.getMap().getTileObject(location));
        assertEquals(obstacle.getLocation().x, location.x);
        assertEquals(obstacle.getLocation().y, location.y);

        //Move a tile object.
        Point playerLocation = mexican.getLocation();
        Point oldLocation = new Point(playerLocation.x, playerLocation.y);
        Point nextLocation = new Point(playerLocation.x + 1, playerLocation.y);

        game.changeTileObjectLocation(mexican, nextLocation);

        assertEquals(mexican, game.getMap().getTileObject(nextLocation));
        assertFalse(game.getMap().hasTileObject(oldLocation));
        assertEquals(mexican.getLocation().x, nextLocation.x);
        assertEquals(mexican.getLocation().y, nextLocation.y);

    }

}