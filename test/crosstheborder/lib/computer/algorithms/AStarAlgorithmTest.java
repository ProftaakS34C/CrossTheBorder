package crosstheborder.lib.computer.algorithms;

import crosstheborder.lib.Game;
import crosstheborder.lib.Map;
import crosstheborder.lib.Tile;
import crosstheborder.lib.User;
import crosstheborder.lib.computer.PathingAlgorithm;
import crosstheborder.lib.enumeration.ObstacleType;
import crosstheborder.lib.interfaces.GameSettings;
import crosstheborder.lib.player.entity.BorderPatrol;
import crosstheborder.lib.player.entity.Mexican;
import crosstheborder.lib.tileobject.Obstacle;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Deque;

import static org.junit.Assert.*;

/**
 * @author Oscar de Leeuw
 */
public class AStarAlgorithmTest {
    private Game game = new Game("empty");
    private Map map = game.getMap();
    private User trump = new User("Trump");
    private User american = new User("American");
    private User mexican = new User("Mexican");
    private GameSettings settings = game.getSettings();
    private PathingAlgorithm algo = new AStarAlgorithm();

    private Mexican mex;
    private BorderPatrol bp;

    @Before
    public void setUp() throws Exception {
        game.addPlayer(trump);
        game.addPlayer(mexican);
        game.addPlayer(american);

        mex = (Mexican) mexican.getPlayer();
        bp = (BorderPatrol) american.getPlayer();

        game.movePlayerEntity(bp, new Point(20, 0));
        game.movePlayerEntity(mex, new Point(20, 30));
    }

    @Test
    public void calculateFreePath() throws Exception {
        Tile current = bp.getTile();
        Tile goal = map.getTile(current.getLocation().x, current.getLocation().y + 5);

        Deque<Tile> path = algo.calculatePath(map, bp, current, goal);
        assertNotNull(path);
        assertFalse(path.isEmpty());

        for (int i = 0; i < path.size(); i++) {
            Tile next = path.pollFirst();
            assertEquals(next, map.getTile(current.getLocation().x, current.getLocation().y + 1));
            current = next;
        }
    }

    @Test
    public void calculatePathOneObstacle() throws Exception {
        Tile current = mex.getTile();
        Point currentLoc = current.getLocation();
        Tile goal = map.getTile(currentLoc.x - 10, currentLoc.y);

        game.changeTileObjectLocation(new Obstacle(ObstacleType.ROCK), map.getTile(currentLoc.x - 5, currentLoc.y));

        Deque<Tile> path = algo.calculatePath(map, mex, current, goal);
        assertNotNull(path);
        assertFalse(path.isEmpty());

        for (int i = 0; i < path.size(); i++) {
            Tile next = path.pollFirst();
            currentLoc = current.getLocation();
            if (i != 4 && i != 11) {
                assertEquals(next, map.getTile(currentLoc.x - 1, currentLoc.y));
            } else {
                assertEquals(next, map.getTile(currentLoc.x, currentLoc.y - 1));
            }

            current = next;
        }
    }

    @Test
    public void pathPerformance1() throws Exception {
        Tile goal = map.getTile(9, 3);
        Deque<Tile> path = algo.calculatePath(map, mex, mex.getTile(), goal);
        assertEquals(path.peekLast(), goal);
    }

    @Test
    public void pathPerformance2() throws Exception {
        Tile goal = mex.getTile();
        Deque<Tile> path = algo.calculatePath(map, bp, bp.getTile(), goal);
        assertEquals(path.peekLast(), goal);
    }

}