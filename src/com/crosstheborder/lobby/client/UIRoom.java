package com.crosstheborder.lobby.client;

import com.crosstheborder.lobby.shared.IRoom;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yannic
 */
public class UIRoom {

    private IRoom room;
    private static final Logger LOGGER = Logger.getLogger(UIRoom.class.getName());

    public UIRoom(IRoom room){
        this.room = room;
    }

    public IRoom getRoom() {
        return room;
    }

    public String getName(){
        String name;
        try{
            name = room.getName();
        }catch (RemoteException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
            name = "ERROR";
        }
        return name;
    }
    public boolean getIsPrivate(){
        boolean isPrivate;
        try{
            isPrivate = room.getIsPrivate();
        }catch (RemoteException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
            isPrivate = true;
        }
        return isPrivate;
    }

    public String getUserAmount(){
        String inRoom = "0";
        String maxPlrs = "0";
        try{
            inRoom = Integer.toString(room.getUsers().size());
            maxPlrs = Integer.toString(room.getMaxPlayers());
        } catch (RemoteException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return inRoom +"/"+maxPlrs;
    }


}
