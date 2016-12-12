package com.crosstheborder.game.shared.factory;


import com.crosstheborder.game.shared.CrossTheBorderGame;
import com.crosstheborder.game.shared.util.CrossTheBorderGameSettings;
import com.sstengine.country.Country;
import com.sstengine.map.Map;
import com.sstengine.player.Player;
import com.sstengine.team.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Oscar de Leeuw
 */
public class MainFactory {

    TeamFactory teamFactory = new TeamFactory();
    CountryFactory countryFactory = new CountryFactory();
    ObstacleFactory obstacleFactory = new ObstacleFactory();
    TileFactory tileFactory = new TileFactory();
    PlayerFactory playerFactory = new PlayerFactory();

    CrossTheBorderGameSettings settings = new CrossTheBorderGameSettings();

    List<Team> teams = new ArrayList<>();

    Team usaTeam;
    Team mexTeam;

    Country USACountry;
    Country MEXCountry;

    Map map;

    public CrossTheBorderGame createGame(String mapName, List<String> names) {

        // Creating USA team
        USACountry = countryFactory.createUSA();
        usaTeam = teamFactory.createTeam(USACountry);
        teams.add(usaTeam);

        // Creating MEX team
        MEXCountry = countryFactory.createMexico();
        mexTeam = teamFactory.createTeam(MEXCountry);
        teams.add(mexTeam);

        // Building Map
        map = CrossTheBorderMapLoader.getInstance().buildMap(mapName, USACountry, MEXCountry, obstacleFactory, tileFactory);

        CrossTheBorderGame game = new CrossTheBorderGame(settings, map, teams);
        createAllPlayers(names).forEach(game::addPlayer);
        game.respawnAllPlayers();
        return game;
    }

    public Player createNewPlayer(String name, PlayerType type) {
        switch (type) {
            case Trump:
                return playerFactory.createUsaLeader(name, usaTeam);
            case Mexican:
                return playerFactory.createMexicanPlayerEntity(name, mexTeam);
            case BorderPatrol:
                return playerFactory.createUsaPlayerEntity(name, usaTeam);
        }

        throw new IllegalArgumentException("PlayerType is non static member");
    }

    private List<Player> createAllPlayers(List<String> names){
        List<Player> players = new ArrayList<>();

        Collections.shuffle(names);

        players.add(createNewPlayer(names.get(0), PlayerType.Trump));
        names.remove(0);

        for(int i = 0; i < names.size(); i++) {
            if(i % 2 == 0){
                players.add(createNewPlayer(names.get(i), PlayerType.Mexican));
            }else{
                players.add(createNewPlayer(names.get(i), PlayerType.BorderPatrol));
            }
        }

        return players;
    }

    private enum PlayerType {
        Trump, Mexican, BorderPatrol
    }
}
