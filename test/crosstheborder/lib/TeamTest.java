package crosstheborder.lib;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Oscar on 27-Sep-16.
 */
public class TeamTest {

    private Team team;

    @Before
    public void setUp() throws Exception {
        team = new Team("USA");
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Looks first if the score equals to 0.
     * Increases the score 10 times with a for-loop.
     * Checks if the score equals to 10.
     *
     * @throws Exception
     */
    @Test
    public void increaseScoreTest() throws Exception {
        Assert.assertEquals(0, team.getScore());

        for(int i = 0; i < 10; i++){

            team.increaseScore();
        }

        Assert.assertEquals(10, team.getScore());
    }
}