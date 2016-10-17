package crosstheborder.lib;

import com.sun.javafx.geom.Rectangle;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by guill on 17-10-2016.
 */
public class MapLoaderTest {

    private Game game;
    private Map map;

    @Before
    public void setUp() throws Exception {
        game = new Game("empty");
        map = game.getMap();
    }

    @Test
    public void buildMap() throws Exception {

        // Checks if map has been made. This should work
        Assert.assertNotNull(map);

        // Checks if the values of the map are correct
        int width = 40;
        int height = 40;
        Rectangle usaArea = new Rectangle(0, 0, 40, 4);
        Rectangle mexArea = new Rectangle(0, 34, 40, 6);

        // Width + Height
        Assert.assertEquals(width, map.getWidth());
        Assert.assertEquals(height, map.getHeight());

        // Tests usa rectangle
        Assert.assertEquals(usaArea.height, map.getUsaArea().height);
        Assert.assertEquals(usaArea.width, map.getUsaArea().width);
        Assert.assertEquals(new Point(usaArea.x, usaArea.y), new Point(map.getUsaArea().x, map.getUsaArea().y));

        // Tests mexico rectangle
        Assert.assertEquals(mexArea.height, map.getMexicoArea().height);
        Assert.assertEquals(mexArea.width, map.getMexicoArea().width);
        Assert.assertEquals(new Point(mexArea.x, mexArea.y), new Point(map.getMexicoArea().x, map.getMexicoArea().y));
    }

}