package crosstheborder.lib;

import crosstheborder.lib.enums.MoveDirection;
import crosstheborder.lib.player.Trump;
import crosstheborder.lib.player.entity.BorderPatrol;
import crosstheborder.lib.player.entity.Mexican;
import crosstheborder.lib.tileobject.Wall;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by guill on 3-10-2016.
 */
public class GameTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        game.addPlayer(new Trump("Trump"));
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Looks if the playerEntity ("Henkie") has been moved,
     * this can we see if we return a true value.
     *
     * @throws Exception
     */
    @Test
    public void movePlayer() throws Exception {

        BorderPatrol bp = new BorderPatrol("Henkie", new Point(50, 70));
        game.addPlayer(bp);

        Assert.assertTrue(game.movePlayer(MoveDirection.DOWN, bp));
    }

    /**
     * This will call the addObstacle Method.
     * It wil send a point, tileObject and player with it.
     * And it looks at the end if the tile has been added.
     *
     * @throws Exception
     */
    @Test
    public void addObstacle() throws Exception {
        Assert.assertTrue(game.addObstacle(new Point(90,60), new Wall(), new Trump("Trump")));
    }

    /**
     * Tests if the respawing of a mexican works.
     * First add a new mexican to the game. Than move that mexican.
     * At last look if the score of mex has changed.
     *
     * @throws Exception
     */
    @Test
    public void respawnMexican() throws Exception {
        Mexican mex = new Mexican("Juan", new Point(40, 40));
        game.addPlayer(mex);
        Assert.assertTrue(game.movePlayer(MoveDirection.UP, mex));
        Assert.assertEquals(1, game.getScoreMexico());
    }

    @Test
    public void update() throws Exception {

    }

}