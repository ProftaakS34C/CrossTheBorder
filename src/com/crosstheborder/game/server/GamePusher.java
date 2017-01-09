package com.crosstheborder.game.server;

import com.crosstheborder.game.shared.CrossTheBorderGame;
import com.crosstheborder.game.shared.network.RMIConstants;
import com.sstengine.event.EventLog;
import fontyspublisher.RemotePublisher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by guill on 6-12-2016.
 *
 * @author Oscar de Leeuw
 */
public class GamePusher extends TimerTask {
    private static final Logger LOGGER = Logger.getLogger(GamePusher.class.getName());

    private String bindingName;
    private RemotePublisher publisher;
    private Registry registry;

    private CrossTheBorderGame game;

    public GamePusher(CrossTheBorderGame game, String bindingName) {
        this.game = game;
        this.bindingName = bindingName;

        createRegistry();
    }

    private void createRegistry() {
        // Created a publisher that will push the game
        try {
            publisher = new RemotePublisher();

            try {
                LOGGER.log(Level.INFO, "Creating registry at port " + RMIConstants.REGISTRY_PORT);
                //registry = LocateRegistry.getRegistry(RMIConstants.REGISTRY_PORT);
                registry = LocateRegistry.createRegistry(RMIConstants.REGISTRY_PORT);
                LOGGER.log(Level.INFO, "Created registry.");
            } catch (RemoteException e) {
                LOGGER.log(Level.INFO, "Could not create registry, getting it instead.");
                //registry = LocateRegistry.createRegistry(RMIConstants.REGISTRY_PORT);
                registry = LocateRegistry.getRegistry(RMIConstants.REGISTRY_PORT);
                LOGGER.log(Level.INFO, "Got registry.");
            }

            try {
                LOGGER.log(Level.INFO, "Trying to bind publisher.");
                registry.bind(bindingName, publisher);
            } catch (Exception e) {
                LOGGER.log(Level.INFO, "Could not bind, trying to rebind publisher.");
                registry.rebind(bindingName, publisher);
            }

            LOGGER.log(Level.INFO, "Publisher has been bound.");

            try {
                LOGGER.log(Level.INFO, "Trying to bind game.");
                registry.bind(bindingName + "game", game);
            } catch (Exception e) {
                LOGGER.log(Level.INFO, "Could not bind, trying to rebind game.");
                registry.rebind(bindingName + "game", publisher);
            }

            LOGGER.log(Level.INFO, "Game has been bound.");

            LOGGER.log(Level.INFO, "Registering game property.");
            publisher.registerProperty(RMIConstants.GAME_PROPERTY_NAME);
            LOGGER.log(Level.INFO, "Registered game property.");

        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            System.exit(3);
        }
    }

    private void updateClients(EventLog log) {
        try {
            publisher.inform(RMIConstants.GAME_PROPERTY_NAME, null, log);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void run() {
        EventLog log = game.update();
        updateClients(log);

        if (game.isDone()) {
            LOGGER.log(Level.INFO, "Game is done.");

            try {
                publisher.inform(RMIConstants.GAME_PROPERTY_NAME, null, true);
                publisher.unregisterProperty(RMIConstants.GAME_PROPERTY_NAME);
                LOGGER.log(Level.INFO, "Unbound property.");
                registry.unbind(bindingName);
                LOGGER.log(Level.INFO, "Unbound registry.");
            } catch (RemoteException | NotBoundException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }

            cancel();
            LOGGER.log(Level.INFO, "Canceled timer.");
        }

        testSize(game, "Game");
        testSize(log, "A log");
        //testSize(game.getMap().getTile(20,20), "a tile");
        //testSize(game.getMap().getTile(0,0).getObstacle(), "an obstacle");
    }

    private void testSize(Object object, String message) {
        File temp = new File("temp.dat");
        temp.deleteOnExit();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(temp))) {
            oos.writeObject(object);

            LOGGER.log(Level.INFO, "Amount of bytes for " + message + ": " + temp.length());
            oos.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        temp.delete();
    }
}
