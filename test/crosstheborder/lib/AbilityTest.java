package crosstheborder.lib;

import crosstheborder.lib.ability.Crawler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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


    @Test(expected = UnsupportedOperationException.class)
    public void getReadyToUse() {
        Assert.assertTrue(crawler.getReadyToUse());
        crawler.useAbility();
        Assert.assertFalse(crawler.getReadyToUse());
    }

    @Test
    public void useAbility() throws Exception {

    }

}