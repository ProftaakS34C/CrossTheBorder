package crosstheborder.lib.player;

import crosstheborder.lib.Player;
import crosstheborder.lib.interfaces.TileObject;

import java.awt.*;
/**
 * Created by Oscar on 26-Sep-16.
 * Player entity is the super class for the Mexican and border patrol.
 */
public abstract class PlayerEntity extends Player implements TileObject {

    protected Point location;
    protected boolean isPassable;

    /**
     * Abstract constructor that passes the name to the Player class.
     * Calls the {@link Player#Player(String)} constructor.
     *
     * @param name The name of the player.
     * @param location The location of the player.
     */
    public PlayerEntity(String name, Point location) {
        super(name);
        this.location = location;
    }

    /**
     * Method which returns if the players can move or not
     *
     * @param moveDirection direction.
     * @return if player can move.
     */
    public boolean moveDirection(String moveDirection){
        return true;
    }

}
