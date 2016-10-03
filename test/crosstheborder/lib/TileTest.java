package crosstheborder.lib;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * Created by Oscar on 27-Sep-16.
 */
public class TileTest {

    private Tile tile;

    @Before
    public void setUp() throws Exception {
        tile = new Tile(new Point(2,2));
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Tests if the hasTileObject method works, first is should return False
     * Then it will fill the tileObject and it should return true.
     *
     * @throws Exception
     */
    @Test
    public void hasTileObjectTest() throws Exception {
        Assert.assertFalse(tile.hasTileObject());

        tile.setTileObject(new TileObject());
        Assert.assertTrue(tile.hasTileObject());
    }
}