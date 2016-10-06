package crosstheborder.lib.player;

import crosstheborder.lib.InputBuffer;
import crosstheborder.lib.Player;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.player.entity.Mexican;

import java.awt.*;
/**
 * Player entity is the super class for the Mexican and border patrol.
 *
 * @author Oscar de Leeuw
 */
public abstract class PlayerEntity extends Player implements TileObject {

    protected Point location;
    protected boolean isPassable;
    private InputBuffer inputBuffer;

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
        this.inputBuffer = new InputBuffer();
    }

    /**
     * This method returns location of the player
     *
     * @return Location of the player
     */
    public abstract Point getLocation();

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
    public boolean moveDirection(MoveDirection moveDirection, int tilewidth){

        switch (moveDirection){
            case UP:
                    location = new Point(location.x, location.y - tilewidth);
                return true;
            case DOWN:
                    location = new Point(location.x, location.y + tilewidth);
                return true;
            case LEFT:
                    location = new Point(location.x - tilewidth, location.y);
                return true;
            case RIGHT:
                    location = new Point(location.x + tilewidth, location.y);
                return true;
            case NONE:
                this.location = location;
                return true;
        }

        return false;
    }
    

    public InputBuffer getInputBuffer() {
        return this.inputBuffer;
    }


}
