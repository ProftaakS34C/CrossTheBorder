package crosstheborder.lib;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

/**
 * @author guill
 */
public class MessageTest {

    private Message mes;

    @Before
    public void setUp() throws Exception {
        mes = new Message(new User("Henk"), "Wat een mooi spel is dit!");
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Looks if the toString method works int he way we want it.
     *
     * @throws Exception
     */
    @Test
    public void toStringTest() throws Exception {
        Assert.assertEquals(LocalTime.now().getHour() + ":0" + LocalTime.now().getMinute() +
                                        " Henk: Wat een mooi spel is dit!", mes.toString());
    }
}