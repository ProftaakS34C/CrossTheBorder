package com.crosstheborder.game.client;

import com.crosstheborder.game.shared.network.RMIConstants;
import com.sstengine.Game;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;

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
public class GameReceiver
        extends UnicastRemoteObject
        implements IRemotePropertyListener{

    private final static Logger LOGGER = Logger.getLogger(GameReceiver.class.getName());

    private IRemotePublisherForListener publisher;

    private Game game;

    public GameReceiver(String publisherName) throws RemoteException {
        Registry registry;

        try {
            LOGGER.log(Level.INFO, "Locating registry..");
            registry = LocateRegistry.getRegistry("localhost", RMIConstants.REGISTRY_PORT);
            LOGGER.log(Level.INFO, "Found registry!");


            LOGGER.log(Level.INFO, "Looking for game under " + publisherName);
            publisher = (IRemotePublisherForListener) registry.lookup(publisherName);
            LOGGER.log(Level.INFO, "Found publisher!");

            publisher.subscribeRemoteListener(this, "game");
            LOGGER.log(Level.INFO, "Found the game property!");

        } catch (NotBoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            LOGGER.log(Level.INFO, "Could not locate a publisher under " + publisherName);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) throws RemoteException {
        game = (Game) propertyChangeEvent.getNewValue();
        LOGGER.log(Level.INFO, "Received new game status!");
    }
}
