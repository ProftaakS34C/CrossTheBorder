package com.crosstheborder.game.client;

import com.sstengine.Game;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;

import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by guill on 6-12-2016.
 */
public class GameReceiver
        extends UnicastRemoteObject
        implements IRemotePropertyListener{

    private final static int REGISTRYPORT = 1099;
    private final static String PUBLISHER_BINDING_NAME = "HenkArieHansPietje";

    private IRemotePublisherForListener publisher;

    private Game game;

    public GameReceiver() throws RemoteException {

        try{
            Registry registry = LocateRegistry.getRegistry("localhost", REGISTRYPORT);
            publisher = (IRemotePublisherForListener) registry.lookup(PUBLISHER_BINDING_NAME);

        } catch (NotBoundException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) throws RemoteException {
        game = (Game) propertyChangeEvent.getNewValue();
        System.out.println("Received new game status!");
    }
}
