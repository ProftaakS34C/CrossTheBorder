package com.crosstheborder.game.shared;

import com.crosstheborder.game.client.input.TrumpInput;
import com.crosstheborder.game.shared.factory.ObstacleFactory;
import com.crosstheborder.game.shared.network.RMIConstants;
import com.sstengine.Game;
import com.sstengine.game.GameSettings;
import com.sstengine.map.Map;
import com.sstengine.map.tile.Tile;
import com.sstengine.obstacle.placeableobstacle.PlaceableObstacle;
import com.sstengine.player.PlayerInput;
import com.sstengine.player.leader.LeaderInput;
import com.sstengine.team.Team;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * @author Oscar de Leeuw
 */
public class CrossTheBorderGame extends Game implements IGame {
    private transient ObstacleFactory obstacleFactory;

    public CrossTheBorderGame(GameSettings settings, Map map, List<Team> teams) {
        super(settings, map, teams);
        this.obstacleFactory = new ObstacleFactory();

        try {
            UnicastRemoteObject.exportObject(this, RMIConstants.REGISTRY_PORT);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pushInput(int playerId, PlayerInput input) {
        if (input instanceof TrumpInput) {
            PlaceableObstacle obs = obstacleFactory.createPlaceableObstacle(((TrumpInput) input).getType());
            Tile tile = getMap().getTile(((TrumpInput) input).getTile().getLocation());
            input = new LeaderInput(obs, tile);
        }

        super.pushInput(playerId, input);
    }
}
