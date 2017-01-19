package crosstheborder.lib;

import com.crosstheborder.lobby.shared.IRoom;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Represents a user of the game.
 *
 * @author Joram
 * @author Oscar de Leeuw
 * @version 1.0
 */
public class User implements Serializable {
    private String name;
    private IRoom room;
    private boolean isComputer;
    private int ID = 0;

    /**
     * This is the constructor method of the class "User"
     * in the constructor the name of the player is set
     * @param name the name of the user
     */
    public User(String name){
        this.name = name;
    }

    /**
     * Checks if the user is owner of the room
     * @return true if User is owner, false if not or if room is null.
     */
    public boolean isOwnerOfLobby(){
        try {
            return room.getOwner().getID() == (getID());
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method used for filling the owner column.
     *
     * @return A string that represents whether this user is the owner of the room.
     */
    public String getOwner() {
        return isOwnerOfLobby() ? "Yes" : "No";
    }

    /**
     * Gets whether this user should be treated as a computer.
     *
     * @return True when this user is a computer.
     */
    public boolean isComputer() {
        return this.isComputer;
    }

    public String getComputer() {
        return isComputer ? "Yes" : "No";
    }

    /**
     * Turn this user into a computer.
     */
    public void turnIntoComputer() {
        this.isComputer = true;
    }

    /**
     * This method sets the room of the player.
     * @param room the room object the player is part of
     */
    public void setRoom(IRoom room) {
        this.room = room;
    }

    public void leaveRoom() {
        if (this.room != null) {
            try {
                this.room.removeUser(this);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.room = null;
        }
    }

    /**
     * This method is used to get the name of the user
     * @return String the name of the user
     */
    public String getName(){
        return name;
    }

    /**
     * This method gets the Room object this player is part of
     * @return Room the room the user is in null if not in a room.
     */
    public IRoom getRoom() {
        return room;
    }

    public void setID(int id){
        ID = id;
    }

    public int getID(){
        return ID;
    }
}