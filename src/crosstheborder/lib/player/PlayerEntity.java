package crosstheborder.lib.player;

import crosstheborder.lib.Player;
import crosstheborder.lib.enums.MoveDirection;
import crosstheborder.lib.player.entity.Mexican;

import java.awt.*;
/**
 * Created by Oscar on 26-Sep-16.
 * Player entity is the super class for the Mexican and border patrol.
 */
public abstract class PlayerEntity extends Player {

    private Point location;

    /**
     * Abstract constructor that passes the name to the Player class.
     *
     * @param name The name of the player.
     */
    public PlayerEntity(String name, Point location) {
        super(name);
        this.location = location;
    }

    /**
     * This method returns location of the player
     *
     * @return Location of the player
     */
    public Point getLocation(){ return this.location; }

    public void respawn(Point location){
        if(this.getClass().equals(Mexican.class)){
            this.location = location;
        }
    }

    /**
     * Method which returns if the players can move or not
     *
     * @param moveDirection direction.
     * @return if player can move.
     */
    public boolean moveDirection(MoveDirection moveDirection){

        int tilewidth = 10;

        if(moveDirection.equals(MoveDirection.UP)){
            location = new Point(location.x, location.y - tilewidth);
            return true;
        }
        else if(moveDirection.equals(MoveDirection.DOWN)){
            location = new Point(location.x, location.y + tilewidth);
            return true;
        }
        else if(moveDirection.equals(MoveDirection.LEFT)){
            location = new Point(location.x - tilewidth, location.y);
            return true;
        }
        else if(moveDirection.equals(MoveDirection.RIGHT)){
            location = new Point(location.x + tilewidth, location.y);
            return true;
        }
        else if(moveDirection.equals(MoveDirection.NONE)){
            this.location = location;
            return true;
        }
        return false;
    }

}
