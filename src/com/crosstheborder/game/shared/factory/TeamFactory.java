package com.crosstheborder.game.shared.factory;

import com.sstengine.country.Country;
import com.sstengine.team.Team;

/**
 * @author Oscar de Leeuw
 */
public class TeamFactory {

    int count = 0;

    public Team createTeam(Country country){
        count ++;
        return new Team(count, country);
    }
}
