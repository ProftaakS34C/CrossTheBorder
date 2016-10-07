package crosstheborder.lib;

import crosstheborder.lib.player.Trump;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Oscar on 27-Sep-16.
 */
public class UserTest {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User("Henk", new Trump("Trump"));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getName() throws Exception {
        Assert.assertEquals("Henk", user.getName());
    }
}