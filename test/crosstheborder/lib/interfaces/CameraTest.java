package crosstheborder.lib.interfaces;

import crosstheborder.lib.Map;
import crosstheborder.lib.MapLoader;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * @author Oscar de Leeuw
 */
public class CameraTest {
    private Map map;
    private Camera camera1;
    private Camera camera2;
    private Camera camera3;
    private Camera camera4;

    @Before
    public void setUp() throws Exception {
        map = MapLoader.getInstance().buildMap("empty");
        Point point = new Point(20, 20);

        camera1 = map.getCamera(point, 40, 800, 600);
        camera2 = map.getCamera(point, 20, 800, 600);
        camera3 = map.getCamera(point, 40, 799, 599);
        camera4 = map.getCamera(point, 10, 1920, 1080);
    }

    @Test
    public void getAmountOfHorizontalTilesTest() throws Exception {
        assertEquals(800 / 40, camera1.getAmountOfHorizontalTiles());
        assertEquals(800 / 20, camera2.getAmountOfHorizontalTiles());
        assertEquals(800 / 40, camera3.getAmountOfHorizontalTiles());
        assertEquals(1920 / 10, camera4.getAmountOfHorizontalTiles());
    }

    @Test
    public void getAmountOfVerticalTilesTest() throws Exception {
        assertEquals(600 / 40, camera1.getAmountOfVerticalTiles());
        assertEquals(600 / 20, camera2.getAmountOfVerticalTiles());
        assertEquals(600 / 40, camera3.getAmountOfVerticalTiles());
        assertEquals(1080 / 10, camera4.getAmountOfVerticalTiles());
    }

    @Test
    public void draw() {

    }
}