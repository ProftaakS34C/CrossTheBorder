package crosstheborder.lib.computer;


import crosstheborder.lib.Map;

import java.awt.*;
import java.util.Deque;

/**
 * @author Oscar de Leeuw
 */
public interface PathingAlgorithm {
    /**
     * Calculates a path through a given map.
     * Returns a Deque object that should be used as a stack.
     * Path excludes the start. Path includes the goal.
     *
     * @param map   The map that should be traversed.
     * @param start The starting point of the algorithm.
     * @param end   The goal point of the algorithm.
     * @return A Deque with the path in a FIFO order.
     */
    Deque<Point> calculatePath(Map map, Point start, Point end);
}
