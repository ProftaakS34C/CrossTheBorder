package crossTheBorder.lib.player;

import crossTheBorder.lib.Player;
import crossTheBorder.lib.enums.MoveDirection;

import java.awt.*;
/**
 * Created by Oscar on 26-Sep-16.
 * Player entity is the super class for the Mexican and border patrol.
 */
public class PlayerEntity extends Player {
    MoveDirection moveDirection;
    private Point Location;

    public PlayerEntity(String name, MoveDirection moveDirection) {
        super(name);
        this.moveDirection = moveDirection;
    }

    /**
     * Method which returns if the players moves or not
     *
     * @param moveDirection
     * @return
     */
    public boolean move(String moveDirection) {
        return false;
    }

}
