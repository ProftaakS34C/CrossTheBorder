package crosstheborder.lib.enumeration;

import java.awt.*;

/**
 * @author Oscar de Leeuw
 */
public enum Direction {
    EAST, WEST, NORTH, SOUTH;

    static {
        EAST.cartesian = new Point(1, 0);
        WEST.cartesian = new Point(-1, 0);
        NORTH.cartesian = new Point(0, -1);
        SOUTH.cartesian = new Point(0, 1);
    }

    private Point cartesian;

    /**
     * Gets the cartesian representation of the direction.
     *
     * @return A point with the cartesian representation of the direction.
     */
    public Point getCartesianRepresentation() {
        return this.cartesian;
    }
}
