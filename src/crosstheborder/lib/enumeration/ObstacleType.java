package crosstheborder.lib.enumeration;

/**
 * The ObstacleType class is an enumeration that captures all the different types of obstacles.
 * Used to determine what sprite to draw on the screen.
 *
 * @author Oscar de Leeuw
 */
public enum ObstacleType {
    TREE, WATER, ROCK;

    static {
        TREE.code = 't';
        WATER.code = 'w';
        ROCK.code = 'r';
    }

    /**
     * The code for the obstacle type in the .ctbmap file format.
     */
    public char code;
}
