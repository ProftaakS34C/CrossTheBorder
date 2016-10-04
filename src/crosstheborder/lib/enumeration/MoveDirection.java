package crosstheborder.lib.enumeration;

import java.awt.*;

/**
 * Enum that contains the definitions of all possible movements within the game.
 *
 * @author Maurice
 * @author Oscar de Leeuw
 */
public enum MoveDirection {
    UP, DOWN, LEFT, RIGHT, NONE;

    static {
        UP.cartesianTranslation = new Point(0, 1);
        DOWN.cartesianTranslation = new Point(0, -1);
        LEFT.cartesianTranslation = new Point(-1, 0);
        RIGHT.cartesianTranslation = new Point(1, 0);
        NONE.cartesianTranslation = new Point(0, 0);
    }

    private Point cartesianTranslation;

    /**
     * Gets a point that would be the cartesian translation of the movement.
     *
     * @return A point that represents the cartesian translation of the movement.
     */
    public Point getTranslation() {
        return cartesianTranslation;
    }
}
