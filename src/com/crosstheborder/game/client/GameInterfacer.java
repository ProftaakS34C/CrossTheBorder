package com.crosstheborder.game.client;

import com.crosstheborder.game.shared.IGame;
import com.crosstheborder.game.shared.network.RMIConstants;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;
import javafx.application.Platform;

import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by guill on 6-12-2016.
 */
public class GameInterfacer
        extends UnicastRemoteObject
        implements IRemotePropertyListener{

    private final static Logger LOGGER = Logger.getLogger(GameInterfacer.class.getName());

    private IRemotePublisherForListener publisher;

    private IGame game;
    private GameClient client;
    private boolean hasFoundGame;

    public GameInterfacer(String ipAddress, String publisherName, GameClient client) throws RemoteException {
        this.client = client;

        Registry registry;
        try {
            LOGGER.log(Level.INFO, "Locating registry under " + ipAddress + "...");
            registry = LocateRegistry.getRegistry(ipAddress, RMIConstants.REGISTRY_PORT);
            LOGGER.log(Level.INFO, "Found registry!");


            LOGGER.log(Level.INFO, "Looking for game under " + publisherName);
            publisher = (IRemotePublisherForListener) registry.lookup(publisherName);
            LOGGER.log(Level.INFO, "Found publisher!");

            publisher.subscribeRemoteListener(this, RMIConstants.GAME_PROPERTY_NAME);
            LOGGER.log(Level.INFO, "Found the game property!");

        } catch (NotBoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            LOGGER.log(Level.INFO, "Could not locate a publisher under " + publisherName);
        }
    }

    public IGame getGame() {
        return game;
    }

    public void unsubscribeListener() throws RemoteException {
        publisher.unsubscribeRemoteListener(this, RMIConstants.GAME_PROPERTY_NAME);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) throws RemoteException {
        game = (IGame) propertyChangeEvent.getNewValue();

        if (!hasFoundGame) {
            client.createUI(game);
        }

        hasFoundGame = true;
        Platform.runLater(() ->
                client.render()
        );

        //LOGGER.log(Level.INFO, "Received new game status!");
    }
}
