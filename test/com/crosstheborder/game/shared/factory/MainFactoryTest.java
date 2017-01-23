package com.crosstheborder.game.shared.factory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author guillaime
 */
public class MainFactoryTest {

    private MainFactory mainFactory;
    private List<String> names;
    private final static String MAPNAME = "mainmap";

    private final static int MAP_WIDTH = 63;
    private final static int MAP_HEIGHT = 66;

    /**
     * @throws Exception
     *
     * Puts some names into list of names.
     */
    @Before
    public void setUp() throws Exception {
        mainFactory = new MainFactory();
        names = new ArrayList<>();
        names.add("Henk");
        names.add("Arie");
        names.add("Ans");
        names.add("Pietje");
        names.add("Jantje");
    }

    /**
     * @throws Exception
     *
     * In the methode createGame() every country will be created by the countyFactory.
     * After the country has been made the teams will be created from those countries.
     * Every country has its own ID so we can check if the are multiple team by their ID.
     * Next the teams will be added to the list of teams.
     *
     * The map we get gets build by the CrossTheBorderMapLoader, we can check if the map has some tiles.
     * At the end the game will be created and the player will be added to that game.
     */
    @Test
    public void createGame() throws Exception {
        mainFactory.createGame(MAPNAME, names);

        // Check if country and team USA are created correctly
        Assert.assertNotNull(mainFactory.USACountry);
        Assert.assertNotNull(mainFactory.usaTeam);
        Assert.assertEquals(mainFactory.countryFactory.createUSA().getTag(), mainFactory.USACountry.getTag());
        Assert.assertEquals(mainFactory.teamFactory.createTeam(mainFactory.USACountry).getCountry().getTag(), mainFactory.usaTeam.getCountry().getTag());

        // Check if country and team MEX are created correctly
        Assert.assertNotNull(mainFactory.MEXCountry);
        Assert.assertNotNull(mainFactory.mexTeam);
        Assert.assertEquals(mainFactory.countryFactory.createMexico().getTag(), mainFactory.MEXCountry.getTag());
        Assert.assertEquals(mainFactory.teamFactory.createTeam(mainFactory.MEXCountry).getCountry().getTag(), mainFactory.mexTeam.getCountry().getTag());

        // Look if the teams are different by id and the size of the team list
        Assert.assertNotEquals(mainFactory.usaTeam.getId(), mainFactory.mexTeam.getId());
        Assert.assertEquals(2, mainFactory.teams.size());

        // Check if map is loaded
        Assert.assertNotNull(mainFactory.map);
        Assert.assertEquals(MAP_WIDTH, mainFactory.map.getWidth());
        Assert.assertEquals(MAP_HEIGHT, mainFactory.map.getHeight());
        Assert.assertEquals(MAPNAME, mainFactory.map.getName());
    }


    /**
     * @throws Exception
     *
     * The methode createNewPlayer gets called when the Game is being made.
     * First player that will be created is Trump. After that there is a switch between
     * Mexican en American players, one by one till everybody is a player.
     */
    @Test
    public void createNewPlayer() throws Exception {
        mainFactory.createGame(MAPNAME, names);

        // Shuffle collection
        Collections.shuffle(names);

        // Check if player 1 is instance of Trump
        assertEquals(mainFactory.createNewPlayer(names.get(0), MainFactory.PlayerType.Trump).getName(),
                mainFactory.playerFactory.createUsaLeader(names.get(0), mainFactory.usaTeam).getName());
        assertEquals(mainFactory.createNewPlayer(names.get(0), MainFactory.PlayerType.Trump).getTeam().getCountry().getTag(),
                mainFactory.playerFactory.createUsaLeader(names.get(0), mainFactory.usaTeam).getTeam().getCountry().getTag());

        // Check if player 2 is instance of Mexican
        assertEquals(mainFactory.createNewPlayer(names.get(1), MainFactory.PlayerType.Mexican).getName(),
                mainFactory.playerFactory.createMexicanPlayerEntity(names.get(1), mainFactory.mexTeam).getName());
        assertEquals(mainFactory.createNewPlayer(names.get(1), MainFactory.PlayerType.Mexican).getTeam().getCountry().getTag(),
                mainFactory.playerFactory.createMexicanPlayerEntity(names.get(1), mainFactory.mexTeam).getTeam().getCountry().getTag());

        // Check if player 3 is instance of BorderPatrol
        assertEquals(mainFactory.createNewPlayer(names.get(2), MainFactory.PlayerType.BorderPatrol).getName(),
                mainFactory.playerFactory.createUsaPlayerEntity(names.get(2), mainFactory.usaTeam).getName());
        assertEquals(mainFactory.createNewPlayer(names.get(2), MainFactory.PlayerType.BorderPatrol).getTeam().getCountry().getTag(),
                mainFactory.playerFactory.createUsaPlayerEntity(names.get(2), mainFactory.usaTeam).getTeam().getCountry().getTag());

        // Check if player 4 is instance of Mexican
        assertEquals(mainFactory.createNewPlayer(names.get(3), MainFactory.PlayerType.Mexican).getName(),
                mainFactory.playerFactory.createMexicanPlayerEntity(names.get(3), mainFactory.mexTeam).getName());
        assertEquals(mainFactory.createNewPlayer(names.get(3), MainFactory.PlayerType.Mexican).getTeam().getCountry().getTag(),
                mainFactory.playerFactory.createMexicanPlayerEntity(names.get(3), mainFactory.mexTeam).getTeam().getCountry().getTag());
    }

}