package crosstheborder.lib.tileobject.placeable;

import crosstheborder.lib.Game;
import crosstheborder.lib.User;
import crosstheborder.lib.enumeration.PlaceableType;
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
public class WallTest {

    private Game game;
    private GameSettings settings;

    private User user1 = new User("Trump");
    private User user2 = new User("Juan");
    private User user3 = new User("Pete");

    private Trump trump;
    private Mexican mexican;
    private BorderPatrol borderPatrol;

    @Before
    public void setUp() throws Exception {

        game = new Game("empty");
        settings = game.getSettings();

        game.addPlayer(user1);
        game.addPlayer(user2);

        trump = (Trump) user1.getPlayer();
        mexican = (Mexican) user2.getPlayer();
        borderPatrol = (BorderPatrol) user3.getPlayer();
    }

    @Test
    public void canPlaceWithNeighbours() throws Exception {

        // First tileObject
        game.changeTileObjectLocation(new Wall(settings), game.getMap().getTile(5, 11));

        // TileObject @ 6,12. Should be True
        Assert.assertTrue(new Wall(settings).canPlaceWithNeighbours(
                game.getMap().getTile(6, 13).getTileObject(),
                game.getMap().getTile(6, 11).getTileObject(),
                game.getMap().getTile(5, 12).getTileObject(),
                game.getMap().getTile(7, 12).getTileObject()));

        // TileObject @ 6,11. Should be True
        Assert.assertTrue(new Wall(settings).canPlaceWithNeighbours(
                game.getMap().getTile(6, 12).getTileObject(),
                game.getMap().getTile(6, 10).getTileObject(),
                game.getMap().getTile(5, 11).getTileObject(),
                game.getMap().getTile(7, 11).getTileObject()));

    }

}