package crosstheborder.lib;

import crosstheborder.lib.ability.Crawler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guill on 3-10-2016.
 */
public class AbilityTest {

    private Crawler crawler;

    @Before
    public void setUp() throws Exception {
        crawler = new Crawler(5);
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Look if the abillity is ready to use.
     * Use the ability and than look if the ability is still usable.
     *
     * @throws Exception
     */
    @Test
    public void getReadyToUse() throws Exception {
        Assert.assertTrue(crawler.getReadyToUse());
        crawler.useAbility();
        Assert.assertFalse(crawler.getReadyToUse());
    }

    @Test
    public void useAbility() throws Exception {

    }

}