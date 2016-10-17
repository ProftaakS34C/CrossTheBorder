package crosstheborder.lib.tileobject;

import crosstheborder.lib.Game;
import crosstheborder.lib.Tile;
import crosstheborder.lib.User;
import crosstheborder.lib.enumeration.Country;
import crosstheborder.lib.enumeration.ObstacleType;
import crosstheborder.lib.enumeration.TileType;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.Trump;
import crosstheborder.lib.player.entity.Mexican;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * @author guillaime
 */
public class ObstacleTest {

    private Game game;

    private User user1 = new User("Trump");
    private User user = new User("Juan");

    private Mexican mexican;
    private Trump trump;

    @Before
    public void setUp() throws Exception {

        game = new Game("empty");

        // Adds 2 players to the game
        game.addPlayer(user1);
        game.addPlayer(user);

        // Places a player on the map
        mexican = (Mexican) user.getPlayer();
        game.changePlayerEntityLocation(mexican, game.getMap().getTile(1, 0));
    }

    @Test
    public void interactWith() throws Exception {

        // Interacts with a tree as obstacle
        game.changeTileObjectLocation(new Obstacle(ObstacleType.TREE), game.getMap().getTile(1, 1));
        game.movePlayerEntity(mexican, new Point(1, 1));
        Assert.assertEquals(mexican.getLocation(), game.getMap().getTile(1, 0).getLocation());

        // Interacts with a rock as obstacle
        game.changeTileObjectLocation(new Obstacle(ObstacleType.ROCK), game.getMap().getTile(1, 1));
        game.movePlayerEntity(mexican, new Point(1, 1));
        Assert.assertEquals(mexican.getLocation(), game.getMap().getTile(1, 0).getLocation());

        // Interacts with water as obstacle
        game.changeTileObjectLocation(new Obstacle(ObstacleType.WATER), game.getMap().getTile(1, 1));
        game.movePlayerEntity(mexican, new Point(1, 1));
        Assert.assertEquals(mexican.getLocation(), game.getMap().getTile(1, 0).getLocation());
    }
}