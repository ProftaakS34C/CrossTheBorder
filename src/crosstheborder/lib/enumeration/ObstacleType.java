package crosstheborder.lib.enumeration;

/**
 * The ObstacleType class is an enumeration that captures all the different types of obstacles.
 * Used to determine what sprite to draw on the screen.
 *
 * @author Oscar de Leeuw
 */
public enum ObstacleType {
    Tree, Water, Rock;

    static {
        Tree.code = 't';
        Water.code = 'w';
        Rock.code = 'r';
    }

    /**
     * The code for the obstacle type in the .ctbmap file format.
     */
    public char code;
}
