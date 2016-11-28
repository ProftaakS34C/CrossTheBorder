package com.crosstheborder.lobby.shared;

import crosstheborder.lib.Message;
import crosstheborder.lib.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guill on 22-11-2016.
 */
public interface IRoom extends Remote{

    List<Message> getMessages() throws RemoteException;
    ArrayList<User> getUsers() throws RemoteException;
    void startGame(String mapName) throws RemoteException;
    boolean addMessage(Message msg) throws RemoteException;
    boolean addUser(User user) throws RemoteException;
    boolean removeUser(User user)throws RemoteException;
    void changePassword(String pswd) throws RemoteException;
    User getOwner() throws RemoteException;
    String getUserAmount() throws RemoteException;
    String getIsPrivate() throws RemoteException;

}
