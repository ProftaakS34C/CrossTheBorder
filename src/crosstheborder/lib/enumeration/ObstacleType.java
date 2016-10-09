package crosstheborder.lib.enumeration;

import crosstheborder.lib.MapLoader;

/**
 * The ObstacleType class is an enumeration that captures all the different types of obstacles.
 * Used to determine what sprite to draw on the screen.
 *
 * @author Oscar de Leeuw
 */
public enum ObstacleType {
    Tree("t"), Water("w"), Rock("r");

    ObstacleType(String code) {
        MapLoader.getInstance().registerObstacleTypeCode(code, this);
    }
}
