package com.crosstheborder.game.shared;

import com.sstengine.game.GameSettings;
import com.sstengine.map.Map;
import com.sstengine.player.Player;
import com.sstengine.player.PlayerInput;
import com.sstengine.team.Team;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

/**
 * @author Oscar de Leeuw
 */
public interface IGame extends Remote {
    Map getMap() throws RemoteException;

    GameSettings getSettings() throws RemoteException;

    List<Team> getTeams() throws RemoteException;

    List<Player> getPlayers() throws RemoteException;

    Random getRandom() throws RemoteException;

    boolean isDone() throws RemoteException;

    int getElapsedTurns() throws RemoteException;

    int getRemainingTurns() throws RemoteException;

    void pushInput(int playerId, PlayerInput input) throws RemoteException;
}
