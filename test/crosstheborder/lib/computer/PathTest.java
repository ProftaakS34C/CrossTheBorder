package crosstheborder.lib.computer;

import crosstheborder.lib.Game;
import crosstheborder.lib.Map;
import crosstheborder.lib.Tile;
import crosstheborder.lib.User;
import crosstheborder.lib.computer.algorithms.AStarAlgorithm;
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
public class PathTest {
    private Game game = new Game("empty");
    private Map map = game.getMap();
    private User trump = new User("Trump");
    private User american = new User("American");
    private User mexican = new User("Mexican");
    private GameSettings settings = game.getSettings();
    private PathingAlgorithm algo = new AStarAlgorithm();
    private Path path = new Path(algo);
    private Deque<Tile> control;
    private Tile goal;
    private Tile start;

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

        start = bp.getTile();
        goal = map.getTile(start.getLocation().x, start.getLocation().y + 5);
        path.calculateNewPath(start, goal, map, bp);
        control = new AStarAlgorithm().calculatePath(map, bp, start, goal);
    }

    @Test
    public void getNextLocation() throws Exception {
        for (int i = 0; i < control.size(); i++) {
            Tile next = path.getNextLocation();
            assertEquals(next, control.pollFirst());
        }

        Path test = new Path(algo);
        assertNull(test.getNextLocation());
    }

    @Test
    public void getEndTile() throws Exception {
        Tile end = path.getEndTile();
        assertEquals(end, goal);
    }

    @Test
    public void age() throws Exception {
        int initAge = path.getAge();
        path.age();

        assertEquals(initAge + 1, path.getAge());
    }

    @Test
    public void contains() throws Exception {
        assertTrue(path.contains(goal));
        for (Tile tile : control) {
            assertTrue(path.contains(tile));
        }
    }

    @Test
    public void calculateNewPath() throws Exception {
        Tile current = mex.getTile();
        Point currentLoc = current.getLocation();
        Tile end = map.getTile(currentLoc.x - 10, currentLoc.y);

        game.changeTileObjectLocation(new Obstacle(ObstacleType.ROCK), map.getTile(currentLoc.x - 5, currentLoc.y));
        path.calculateNewPath(current, end, map, mex);
        Deque<Tile> localControl = new AStarAlgorithm().calculatePath(map, mex, current, end);

        for (int i = 0; i < localControl.size(); i++) {
            Tile next = path.getNextLocation();
            assertEquals(next, localControl.pollFirst());
        }
    }

    @Test
    public void precedePath() throws Exception {
        Tile newStart = map.getTile(start.getLocation().x - 5, start.getLocation().y);

        path.precedePath(newStart, map, bp);
        Deque<Tile> localControl = new AStarAlgorithm().calculatePath(map, bp, newStart, goal);

        assertEquals(localControl.size(), path.size());
    }

    @Test
    public void extendPath() throws Exception {
        Tile newGoal = map.getTile(goal.getLocation().x + 5, goal.getLocation().y + 5);

        path.extendPath(start, newGoal, map, bp);
        Deque<Tile> localControl = new AStarAlgorithm().calculatePath(map, bp, start, newGoal);

        for (int i = 0; i < localControl.size(); i++) {
            Tile next = path.getNextLocation();
            assertEquals(next, localControl.pollFirst());
        }
    }
}