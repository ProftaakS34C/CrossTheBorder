package com.crosstheborder.game.server;

import com.sstengine.Game;
import fontyspublisher.RemotePublisher;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.TimerTask;

/**
 * Created by guill on 6-12-2016.
 */
public class GamePusher extends TimerTask {

    private static final int REGISTRYNUMBER = 1099;
    private String bindingName;
    private RemotePublisher publisher;

    private Game game;

    public GamePusher(Game game, String bindingName){
        this.game = game;
        this.bindingName = bindingName;

        createRegistry();
    }

    private void createRegistry(){

        // Created a publisher that will push the game
        try{
            publisher = new RemotePublisher();

        } catch (RemoteException e) {
            e.printStackTrace();
            return;
        }

        // Create registry on port 1099
        Registry registry = null;
        try{
            registry = LocateRegistry.createRegistry(REGISTRYNUMBER);

        } catch (RemoteException e) {
            e.printStackTrace();
            return;
        }

        // Bind publisher to registry
        try{
            registry.rebind(bindingName, publisher);

        } catch (RemoteException e) {
            e.printStackTrace();
            return;
        }

        // Set property on publisher
        try{
            publisher.registerProperty("game");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void updateClients(Game newGameStatus) throws RemoteException{
        this.game = newGameStatus;

        try{
            publisher.inform("game", null, game);
        }catch (Exception e){
            System.out.println("Publisher not found!");
        }
    }

    @Override
    public void run() {
        try {
            updateClients(game);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
