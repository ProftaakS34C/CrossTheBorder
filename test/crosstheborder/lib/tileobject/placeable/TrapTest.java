package crosstheborder.lib.tileobject.placeable;

import crosstheborder.lib.Game;
import crosstheborder.lib.User;
import crosstheborder.lib.ability.Crawler;
import crosstheborder.lib.enumeration.ObstacleType;
import crosstheborder.lib.enumeration.TileType;
import crosstheborder.lib.interfaces.GameSettings;
import crosstheborder.lib.player.Trump;
import crosstheborder.lib.player.entity.BorderPatrol;
import crosstheborder.lib.player.entity.Mexican;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author guill
 */
public class TrapTest {

    private Game game;
    private GameSettings settings;

    @Before
    public void setUp() throws Exception {
        game = new Game("empty");
        settings = game.getSettings();
    }

    @Test
    public void canPlaceWithNeighbours() throws Exception {

        // First tileObject
        game.changeTileObjectLocation(new Trap(settings), game.getMap().getTile(5, 11));

        // TileObject @ 4,11. Should be False
        Assert.assertFalse(new Trap(settings).canPlaceWithNeighbours(
                game.getMap().getTile(4, 12).getTileObject(),
                game.getMap().getTile(4, 10).getTileObject(),
                game.getMap().getTile(3, 11).getTileObject(),
                game.getMap().getTile(5, 11).getTileObject()));

        // TileObject @ 6,12. Should be True
        Assert.assertTrue(new Trap(settings).canPlaceWithNeighbours(
                game.getMap().getTile(6, 13).getTileObject(),
                game.getMap().getTile(6, 11).getTileObject(),
                game.getMap().getTile(5, 12).getTileObject(),
                game.getMap().getTile(7, 12).getTileObject()));

    }
}