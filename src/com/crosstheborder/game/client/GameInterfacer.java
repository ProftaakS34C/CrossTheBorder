package com.crosstheborder.game.client;

import com.crosstheborder.game.shared.IGame;
import com.crosstheborder.game.shared.network.RMIConstants;
import com.sstengine.event.EventLog;
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


            LOGGER.log(Level.INFO, "Looking for publisher under " + publisherName);
            publisher = (IRemotePublisherForListener) registry.lookup(publisherName);
            LOGGER.log(Level.INFO, "Found publisher!");

            LOGGER.info("Looking for game under " + publisherName + "game");
            game = (IGame) registry.lookup(publisherName + "game");
            LOGGER.info("Found game!");

            LOGGER.info("Creating UI.");
            client.createUI(game);
            LOGGER.info("Created UI!");

            LOGGER.info("Subscribing to the game property");
            publisher.subscribeRemoteListener(this, RMIConstants.GAME_PROPERTY_NAME);
            LOGGER.log(Level.INFO, "Subscribed to the game property!");

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
        Object newVal = propertyChangeEvent.getNewValue();
        if((newVal instanceof EventLog)) {
            EventLog log = (EventLog) newVal;
            game.executeEventLog(log);

            Platform.runLater(() ->
                    client.render()
            );
        }else {
            client.endGame();
        }



        //LOGGER.log(Level.INFO, "Received new game status!");
    }
}
