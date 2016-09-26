package crosstheborder.lib.player;

import crosstheborder.lib.Player;

import java.awt.*;
/**
 * Created by Oscar on 26-Sep-16.
 * Player entity is the super class for the Mexican and border patrol.
 */
public abstract class PlayerEntity extends Player {

    private Point Location;

    public PlayerEntity(String name) {
        super(name);
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
