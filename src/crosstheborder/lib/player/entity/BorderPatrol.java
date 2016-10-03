package crosstheborder.lib.player.entity;

import crosstheborder.lib.player.PlayerEntity;

import java.awt.*;

/**
 * Created by Oscar on 26-Sep-16.
 */
public class BorderPatrol extends PlayerEntity{

    /**
     * Abstract constructor that passes the name to the Player class.
     *
     * @param name The name of the player.
     */
    public BorderPatrol(String name, Point location) {
        super(name, location);

    }
}
