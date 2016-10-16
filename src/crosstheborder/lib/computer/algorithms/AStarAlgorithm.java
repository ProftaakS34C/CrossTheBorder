package crosstheborder.lib.computer.algorithms;

import crosstheborder.lib.Map;
import crosstheborder.lib.computer.PathingAlgorithm;
import crosstheborder.lib.player.PlayerEntity;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Credit goes to http://www.redblobgames.com/pathfinding/a-star/implementation.html
 *
 * @author Oscar de Leeuw
 */
public class AStarAlgorithm implements PathingAlgorithm {
    private HashMap<Point, Point> cameFrom;
    private HashMap<Point, Double> costSoFar;

    /**
     * Creates a new AStarAlgorithm with a given HashMap size.
     *
     * @param expectedPathSize The expected size of the path.
     *                         Can increase performance on larger paths but consumes more memory.
     */
    public AStarAlgorithm(int expectedPathSize) {
        cameFrom = new HashMap<>(expectedPathSize);
        costSoFar = new HashMap<>(expectedPathSize);
    }

    /**
     * Creates a new AStarAlgorithm with the default HashMap size.
     */
    public AStarAlgorithm() {
        this(16); //Default size of the HashMap.
    }

    @Override
    public Deque<Point> calculatePath(Map map, PlayerEntity entity, Point start, Point end) {
        //Perform the algorithm.
        performAStar(map, entity, start, end);

        //Use a Deque as a stack.
        Deque<Point> ret = new ArrayDeque<>();
        //Push the end to the stack.
        ret.offerFirst(end);

        //Construct from the end to the start and set them in reverse order into the Deque.
        Point previousPoint = cameFrom.get(end);
        while (previousPoint != start) {
            //Push the point to the stack.
            ret.offerFirst(previousPoint);
        }

        cameFrom.clear();
        costSoFar.clear();
        return ret;
    }

    /**
     * Gets the outcome of the heuristic function for the A* algorithm.
     * The heuristic function is defined as the straight line (as the crow flies) distance between the given point and the goal.
     *
     * @param location The current location for which the heuristic function should be evaluated.
     * @return The outcome of the heuristic function.
     */
    private double heuristic(Point location, Point goal) {
        return location.distance(goal);
    }

    /**
     * Performs the A* algorithm.
     *
     * @param map   The map that the algorithm should be used upon.
     * @param entity The entity for which to perform the A* search.
     * @param start The starting location of the algorithm.
     * @param end   The goal of the algorithm.
     */
    private void performAStar(Map map, PlayerEntity entity, Point start, Point end) {
        //Create a frontier and push the start to the frontier.
        Frontier<Point> frontier = new Frontier<>();
        frontier.enqueue(start, 0);

        //Initialize cameFrom and costSoFar.
        cameFrom.put(start, start);
        costSoFar.put(start, 0d);

        //While there is still a frontier and the goal hasn't been found.
        while (frontier.size() > 0) {
            //Get the highest priority point.
            Point current = frontier.dequeue();

            //If the location of the current point corresponds to the end end the search.
            if (current.x == end.x && current.y == end.y) {
                //Little hack to make sure the end is present in the cameFrom HashMap.
                Point previous = cameFrom.get(current);
                cameFrom.put(end, previous);
                break;
            }

            //Foreach neighbour of the current location perform the following.
            for (Point next : map.getNeighbours(current, entity)) {
                //Calculate the cost of moving to the next location.
                //Currently 1 since the map will only return tiles which can be freely moved to.
                double newCost = costSoFar.get(current) + 1;
                //If the next location has not been evaluated or the newCost is lower than previously evaluated.
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    //Add the location to the costSoFar HashMap.
                    costSoFar.put(next, newCost);
                    //Calculate the priority for evaluating this location.
                    double priority = newCost + heuristic(next, end);
                    //Add the location to the frontier.
                    frontier.enqueue(next, priority);
                    //Register that the next location was reached through the current location.
                    cameFrom.put(next, current);
                }
            }
        }
    }

    /**
     * Class for representing a frontier.
     * Works with a priority for each item. The priority represents the estimated cost to the goal.
     * <br>
     * Supported operations:
     * <ul>
     * <li>Size: Gets the size of the frontier.</li>
     * <li>Enqueue: Adds an item to the frontier.</li>
     * <li>Dequeue: Retrieves the item with the lowest priority and removes it from the frontier.</li>
     * </ul>
     * <br>
     * Credit goes to http://www.redblobgames.com/pathfinding/a-star/implementation.html
     *
     * @param <T> The type you want a frontier of.
     */
    private class Frontier<T> {
        private final List<Tuple<T, Double>> elements = new ArrayList();

        /**
         * Gets the size of the frontier.
         *
         * @return An integer that represents the size of the frontier.
         */
        public int size() {
            return elements.size();
        }

        /**
         * Adds an item to the frontier.
         *
         * @param t        The item that should be added to the frontier.
         * @param priority The priority of the item.
         */
        public void enqueue(T t, double priority) {
            elements.add(new Tuple<>(t, priority));
        }

        /**
         * Retrieves the item with the lowest priority.
         * Removes that item from the frontier.
         *
         * @return The item with the lowest priority.
         */
        public T dequeue() {
            int bestIndex = 0;

            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).item2 < elements.get(bestIndex).item2) {
                    bestIndex = i;
                }
            }

            T bestItem = elements.get(bestIndex).item1;
            elements.remove(bestIndex);
            return bestItem;
        }

        /**
         * General class for coupling two objects together.
         *
         * @param <X> The first object.
         * @param <Y> The second object.
         */
        private class Tuple<X, Y> {
            public final X item1;
            public final Y item2;

            public Tuple(X item1, Y item2) {
                this.item1 = item1;
                this.item2 = item2;
            }
        }
    }
}
