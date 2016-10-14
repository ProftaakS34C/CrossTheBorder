package crosstheborder.lib.player.entity;

import crosstheborder.lib.Team;
import crosstheborder.lib.enumeration.Country;
import crosstheborder.lib.interfaces.GameManipulator;
import crosstheborder.lib.interfaces.GameSettings;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.tileobject.placeable.Wall;

/**
 * This class represents the Mexican player object.
 * @author Joram
 * @author Oscar de Leeuw
 * @version 1.0
 */
public class Mexican extends PlayerEntity {
    //Indicates how many seconds it will take to climb the wall.
    private final float climbModifier;

    private int climbTicks;
    private Wall climbingWall;

    /**
     * This is the constructor method of the class "Mexican".
     * in the constructor the name of the Mexican is set.
     * Calls the {@link PlayerEntity#PlayerEntity(String, Team, GameSettings)} constructor.
     *
     * @param name    The name of the Mexican.
     * @param team The team this Mexican belongs to.
     * @param settings The settings of the game.
     */
    public Mexican(String name, Team team, GameSettings settings) {
        super(name, team, settings);
        climbModifier = settings.getClimbModifier();
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
            climbTicks = (int) ((wall.getHeight() * climbModifier * serverTickRate));
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
     * {@inheritDoc}
     * <p>
     * If the other playerEntity is a {@link BorderPatrol}, respawn the mexican and raise the score of the BorderPatrol team.
     * </p>
     * Calls the following methods from GameManipulator:
     * <ul>
     * <li>Calls {@link GameManipulator#increaseScore(Team)} when the other entity is a BorderPatrol.</li>
     * <li>Calls {@link GameManipulator#respawnPlayer(PlayerEntity)} with itself when the other entity is a BorderPatrol.</li>
     * </ul>
     */
    @Override
    public boolean interactWith(PlayerEntity player, GameManipulator game) {
        if (player instanceof BorderPatrol) {
            game.increaseScore(player.getTeam());
            game.respawnPlayer(this);
        }
        return false;
    }

    @Override
    public boolean interactWith(Country country, GameManipulator game) {
        switch (country) {
            case USA:
                game.increaseScore(this.getTeam());
                game.respawnPlayer(this);
                return false;
        }
        return true;
    }
}
