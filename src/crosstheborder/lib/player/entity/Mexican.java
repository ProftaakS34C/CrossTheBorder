package crosstheborder.lib.player.entity;

import crosstheborder.lib.Ability;
import crosstheborder.lib.enumeration.TileObjectType;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.tileobject.placeable.Wall;

import java.awt.*;

/**
 * This class represents the Mexican player object.
 * @author Joram
 * @author Oscar de Leeuw
 * @version 1.0
 */
public class Mexican extends PlayerEntity {
    //Indicates how many seconds it will take to climb the wall.
    private static final float CLIMB_MODIFIER = 0.5f; //TODO Gather from GameSettings file.

    private boolean isTrapped;
    private Ability ability;

    private int climbTicks;
    private Wall climbingWall;

    /**
     * This is the constructor method of the class "Mexican".
     * in the constructor the name of the Mexican is set.
     * Calls the {@link PlayerEntity#PlayerEntity(String, Point)} constructor.
     * Sets isPassable to false;
     *
     * @param name    The name of the Mexican.
     * @param location The location of the player.
     * @param ability The ability of the Mexican.
     */
    public Mexican(String name, Point location, Ability ability) {
        super(name, location);
        this.ability = ability;
        isPassable = false;
    }

    /**
     * This method is used to get isTrapped.
     *
     * @return The boolean the value of isTrapped.
     */
    public boolean getIsTrapped() {
        return isTrapped;
    }

    /**
     * Sets the value of the isTrapped field.
     *
     * @param value The new boolean value.
     */
    public void setIsTrapped(boolean value) {
        isTrapped = value;
    }

    /**
     * This method is used when a Mexican uses his ability.
     * the method {@link Ability#useAbility()} of {@link Ability} is called.
     */
    public void useAbility() {
        ability.useAbility();
    }

    /**
     * Climbs a wall.
     *
     * @param wall The wall that should be scaled.
     * @return A boolean value that is true when the wall is successfully scaled.
     */
    public boolean climbWall(Wall wall) {
        //If the current wall isn't being climbed, or is not the same wall, start a new climbing session.
        if (climbingWall == null || !climbingWall.equals(wall)) {
            climbingWall = wall;
            //Set the abilityTime to the total time it will take to scale the wall based on server ticks.
            climbTicks = (int) ((wall.getHeight() * CLIMB_MODIFIER * SERVER_TICK_RATE));
        }
        //If we are still climbing the same wall lower the timer.
        else if (climbingWall.equals(wall)) {
            //Lower amount of ticks needed by one.
            climbTicks--;
            //If the timer runs out reset everything and return a true.
            if (climbTicks <= 0) {
                climbingWall = null;
                climbTicks = 0;
                return true;
            }
        }
        return false;
    }

    /**
     * Method for handling the interaction between two {@link TileObject}s.
     *
     * @param o The TileObject that is interacting with this object.
     */
    @Override
    public void interactWith(TileObject o) {
        if (o.isPassable()) {
            this.location = o.getLocation();
        }

        TileObjectType type = TileObjectType.valueOf(o.getClass().getSimpleName());
        switch (type) {
            case BorderPatrol:
                //TODO add mexican borderpatrol interaction.
                break;
            case Trap:
                if (this.isTrapped) {
                    //TODO increase trapped timer.
                } else {
                    this.isTrapped = true;
                }
                break;
            case Wall:
                //TODO add wall interaction.
                break;
        }
    }

    /**
     * Method for getting whether the object is passable or not.
     *
     * @return A boolean that determines whether the object is passable or not.
     */
    @Override
    public boolean isPassable() {
        return this.isPassable;
    }
}
