package com.crosstheborder.game.shared;

import com.crosstheborder.game.shared.network.RMIConstants;
import com.sstengine.Game;
import com.sstengine.game.GameSettings;
import com.sstengine.map.Map;
import com.sstengine.team.Team;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * @author Oscar de Leeuw
 */
public class CrossTheBorderGame extends Game implements IGame {

    public CrossTheBorderGame(GameSettings settings, Map map, List<Team> teams) {
        super(settings, map, teams);

        try {
            UnicastRemoteObject.exportObject(this, RMIConstants.REGISTRY_PORT);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
