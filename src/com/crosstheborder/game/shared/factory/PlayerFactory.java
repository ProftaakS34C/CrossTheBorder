package com.crosstheborder.game.shared.factory;


import com.crosstheborder.game.shared.util.CrossTheBorderCountryTag;
import com.sstengine.player.Player;
import com.sstengine.player.leader.Leader;
import com.sstengine.player.playerentity.PlayerEntity;
import com.sstengine.team.Team;

/**
 * Created by guill on 5-12-2016.
 */
public class PlayerFactory  {
    private int idCount = 0;

    private LeaderFactory leaderFactory = new LeaderFactory();
    private PlayerEntityFactory playerEntityFactory = new PlayerEntityFactory();

    public Player createUsaLeader(String name, Team team){
        Leader leader = leaderFactory.createTrump();

        return new Player(idCount++, name, team, leader);
    }

    public Player createMexicanPlayerEntity(String name, Team team) {
        PlayerEntity entity = playerEntityFactory.createMexican();

        return new Player(idCount++, name, team, entity);
    }

    public Player createUsaPlayerEntity(String name, Team team) {
        PlayerEntity entity = playerEntityFactory.createBorderPatrol();

        return new Player(idCount++, name, team, entity);
    }
}
