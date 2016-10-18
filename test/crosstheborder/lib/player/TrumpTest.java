package crosstheborder.lib.player;

import crosstheborder.lib.Game;
import crosstheborder.lib.User;
import crosstheborder.lib.enumeration.ObstacleType;
import crosstheborder.lib.enumeration.PlaceableType;
import crosstheborder.lib.interfaces.GameSettings;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.entity.BorderPatrol;
import crosstheborder.lib.player.entity.Mexican;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author guill
 */
public class TrumpTest {

    private Game game;
    private GameSettings settings;

    private User user1 = new User("Trump");
    private User user2 = new User("Juan");
    private User user3 = new User("Pete");

    private Trump trump;

    @Before
    public void setUp() throws Exception {
        game = new Game("empty");
        settings = game.getSettings();

        game.addPlayer(user1);
        game.addPlayer(user2);
        game.addPlayer(user3);

        trump = (Trump) user1.getPlayer();
    }

    @Test
    public void getWallAmount() throws Exception {
        Assert.assertEquals(10, trump.getWallAmount());
        Assert.assertEquals(3, trump.getTrapAmount());

        game.addPlaceable(game.getMap().getTile(5, 11).getLocation(), PlaceableType.TRAP);
        Assert.assertEquals(2, trump.getTrapAmount());

        game.addPlaceable(game.getMap().getTile(6, 11).getLocation(), PlaceableType.WALL);
        game.addPlaceable(game.getMap().getTile(7, 11).getLocation(), PlaceableType.WALL);
        Assert.assertEquals(8, trump.getWallAmount());
    }
}