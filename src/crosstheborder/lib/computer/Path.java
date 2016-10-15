package crosstheborder.lib.computer;

import crosstheborder.lib.Map;

import java.awt.*;
import java.util.Deque;

/**
 * @author Oscar de Leeuw
 */
public class Path {
    private PathingAlgorithm algorithm;
    private Deque<Point> path;
    private int age;

    /**
     * Creates a new Path object with the given algorithm.
     *
     * @param algorithm The pathing algorithm that should be used for pathing.
     */
    public Path(PathingAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Gets the next location on the path.
     * Increases the age of the path by 1.
     *
     * @return The next location of the path.
     */
    public Point getNextLocation() {
        age++;
        return path != null ? path.pollFirst() : null;
    }

    /**
     * Gets but dont remove the goal of the path.
     *
     * @return The endpoint of the path.
     */
    public Point getEndPoint() {
        return path != null ? path.peekLast() : null;
    }

    /**
     * Checks whether a given point is present in the path.
     *
     * @param point The point to check for.
     * @return True when the point is present in the path.
     */
    public boolean contains(Point point) {
        return path.stream().anyMatch(x -> x.equals(point));
    }

    /**
     * Calculates a new path to the given goal.
     * Path excludes the start. Path includes the goal.
     * Resets the age of the path.
     *
     * @param start The starting location of the path. Generally the current location of the entity.
     * @param goal  The end of the path. Generally the goal of the entity.
     * @param map   The map on which the path should be calculated.
     */
    public void calculateNewPath(Point start, Point goal, Map map) {
        this.path = algorithm.calculatePath(map, start, goal);
        age = 0;
    }

    /**
     * Connects a given start to the current path.
     * Polls the first element to make place for the goal from the new calculation.
     *
     * @param start The new start of the path.
     * @param map   The map on which the path should be calculated.
     */
    public void precedePath(Point start, Map map) {
        //Must poll since the goal is going to be added to the map again.
        Point goal = path.pollFirst();
        //Calculate a new path.
        Deque<Point> extraPath = algorithm.calculatePath(map, start, goal);

        //Adds the extraPath in correct order on top of the path.
        for (int i = 0; i < extraPath.size(); i++) {
            //Get the last point in the extra path.
            Point point = extraPath.pollLast();
            //Push it to the top of the current path.
            path.offerFirst(point);
        }
    }

    /**
     * Extends the path to a new goal.
     * Will remove an amount of points equal to the age of the path before recalculating the path in order to guaranteed the best path.
     *
     * @param goal The goal of the extension.
     * @param map  The map on which the path should be calculated.
     */
    public void extendPath(Point goal, Map map) {
        for (int i = 0; i < age; i++) {
            path.pollLast();
        }
        Point start = path.peekLast();
        Deque<Point> extraPath = algorithm.calculatePath(map, start, goal);

        //Adds the extraPath in correct order at the bottom of the path.
        for (int i = 0; i < extraPath.size(); i++) {
            //Get the first point in the extra path.
            Point point = extraPath.pollFirst();
            //Push it to the end of the path.
            path.offerLast(point);
        }

        age = 0;
    }
}
