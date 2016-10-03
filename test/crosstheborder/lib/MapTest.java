package crosstheborder.lib;

import crosstheborder.lib.tileobject.Wall;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 *
 */
public class MapTest {

    private Map map;

    @Before
    public void setUp() throws Exception {
        map = new Map("Border", 2, 2);
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Checks if the tile at point 10,10 can be changed.
     * This has to be true because we made en new map
     * without tileObjects at some points
     *
     * @throws Exception
     */
    @Test
    public void canPlaceTileObject() throws Exception {

        Assert.assertTrue(map.canPlaceTileObject(new Point(10, 10)));
    }

    /**
     * Gets the tile at index 3 in the tiles of map.
     * Looks if this equals to point 10,10. This should be the same.
     *
     * Than make a new tileObject. and add it to the tile at Point 10,10
     * Check if you still can change the object. This should return false.
     *
     * @throws Exception
     */
    @Test
    public void changeTileObject() throws Exception {

        Tile t = map.tiles.get(3);
        Assert.assertEquals(new Point(10, 10), t.getLocation());

        TileObject to = new Wall();

        map.changeTileObject(new Point(10, 10), to);
        Assert.assertFalse(map.canPlaceTileObject(new Point(10, 10)));
    }

}