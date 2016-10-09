package crosstheborder.lib;

import crosstheborder.lib.player.Trump;
import crosstheborder.lib.player.entity.BorderPatrol;
import crosstheborder.lib.player.entity.Mexican;
import crosstheborder.lib.tileobject.placeable.Wall;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        game = new Game();

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

        assertTrue(mexican.getTeam().getTeamArea().contains(mexican.getLocation()));
        //TODO add code that checks the map contains a tileObject at the location of the mexican.
        //TODO add code that checks the teams contain the players.
    }

    @Test
    public void update() throws Exception {
        Point initialBPLocation = new Point(borderPatrol.getLocation().x, borderPatrol.getLocation().y);
        Point initialMexLocation = new Point(mexican.getLocation().x, mexican.getLocation().y);

        //Immobilize the mexican.
        mexican.setCanMoveTicks(1);

        //Process the amount of ticks that equals 1 second, which should allow the mexican to move again.
        for (int i = 0; i < 1 * TICK_RATE; i++) {
            //Push movement. up to mexico and down to border patrol


            game.update();
            assertEquals(initialMexLocation.x, mexican.getLocation().x);
            assertEquals(initialMexLocation.y, mexican.getLocation().y);

            initialBPLocation.translate(0, 1);
            assertEquals(initialBPLocation.x, borderPatrol.getLocation().x);
            assertEquals(initialBPLocation.y, borderPatrol.getLocation().y);
        }

        //Push one more up movement to the mexican object.

        //update one more time which should move the mexican.
        game.update();
        assertEquals(initialMexLocation.x, mexican.getLocation().x);
        assertEquals(initialMexLocation.y - 1, mexican.getLocation().y);


    }

    @Test
    public void addObstacle() throws Exception {
        Point location = new Point(20, 20);

        game.addObstacle(location, new Wall(location, 2));

        //TODO check that the object was actually placed at the location.
        //TODO try to add an object at a location that is taken.
    }

    @Test
    public void movePlayerEntity() throws Exception {

    }

    @Test
    public void respawnPlayer() throws Exception {

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

    }

}