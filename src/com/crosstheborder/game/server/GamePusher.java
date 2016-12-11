package com.crosstheborder.game.server;

import com.crosstheborder.game.shared.CrossTheBorderGame;
import com.crosstheborder.game.shared.network.RMIConstants;
import fontyspublisher.RemotePublisher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by guill on 6-12-2016.
 * @author Oscar de Leeuw
 */
public class GamePusher extends TimerTask {
    private static final Logger LOGGER = Logger.getLogger(GamePusher.class.getName());

    private String bindingName;
    private RemotePublisher publisher;

    private CrossTheBorderGame game;

    public GamePusher(CrossTheBorderGame game, String bindingName) {
        this.game = game;
        this.bindingName = bindingName;

        createRegistry();
    }

    private void createRegistry(){
        // Created a publisher that will push the game
        try{
            publisher = new RemotePublisher();

            Registry registry = LocateRegistry.createRegistry(RMIConstants.REGISTRY_PORT);
            registry.rebind(bindingName, publisher);

            publisher.registerProperty(RMIConstants.GAME_PROPERTY_NAME);

        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private void updateClients() throws RemoteException {
        try{
            publisher.inform(RMIConstants.GAME_PROPERTY_NAME, null, game);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void run() {
        try {
            game.update();
            updateClients();
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
