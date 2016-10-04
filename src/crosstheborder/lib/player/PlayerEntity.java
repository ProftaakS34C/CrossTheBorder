package crosstheborder.lib.player;

import crosstheborder.lib.InputBuffer;
import crosstheborder.lib.Player;
import crosstheborder.lib.interfaces.TileObject;

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

    public InputBuffer getInputBuffer() {
        return this.inputBuffer;
    }


}
