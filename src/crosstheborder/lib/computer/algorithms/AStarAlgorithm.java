package crosstheborder.lib.computer.algorithms;

import crosstheborder.lib.Map;
import crosstheborder.lib.Tile;
import crosstheborder.lib.computer.PathingAlgorithm;
import crosstheborder.lib.player.PlayerEntity;

import java.util.*;

/**
 * Credit goes to http://www.redblobgames.com/pathfinding/a-star/implementation.html
 *
 * @author Oscar de Leeuw
 */
public class AStarAlgorithm implements PathingAlgorithm {
    private HashMap<Tile, Tile> cameFrom;
    private HashMap<Tile, Double> costSoFar;

    /**
     * Creates a new AStarAlgorithm with a given HashMap size.
     *
     * @param expectedPathSize The expected size of the path.
     *                         Can increase performance on larger paths but consumes more memory.
     */
    AStarAlgorithm(int expectedPathSize) {
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
    public Deque<Tile> calculatePath(Map map, PlayerEntity entity, Tile start, Tile end) {
        //Perform the algorithm.
        performAStar(map, entity, start, end);

        //Use a Deque as a stack.
        Deque<Tile> ret = new ArrayDeque<>();
        //Push the end to the stack.
        ret.offerFirst(end);

        //Construct from the end to the start and set them in reverse order into the Deque.
        Tile previousTile = cameFrom.get(end);
        while (previousTile != start) {
            //Push the point to the stack.
            ret.offerFirst(previousTile);
        }

        cameFrom.clear();
        costSoFar.clear();
        return ret;
    }

    /**
     * Gets the outcome of the heuristic function for the A* algorithm.
     * The heuristic function is defined as the straight line (as the crow flies) distance between the two given tiles.
     *
     * @param current The current tile for which the heuristic function should be evaluated.
     * @param goal The goal of the pathing algorithm.
     * @return The outcome of the heuristic function.
     */
    private double heuristic(Tile current, Tile goal) {
        return current.getLocation().distance(goal.getLocation());
    }

    /**
     * Performs the A* algorithm.
     *
     * @param map   The map that the algorithm should be used upon.
     * @param entity The entity for which to perform the A* search.
     * @param start The starting location of the algorithm.
     * @param end   The goal of the algorithm.
     */
    private void performAStar(Map map, PlayerEntity entity, Tile start, Tile end) {
        //Create a frontier and push the start to the frontier.
        Frontier<Tile> frontier = new Frontier<>();
        frontier.enqueue(start, 0);

        //Initialize cameFrom and costSoFar.
        cameFrom.put(start, start);
        costSoFar.put(start, 0d);

        //While there is still a frontier and the goal hasn't been found.
        while (frontier.size() > 0) {
            //Get the highest priority point.
            Tile current = frontier.dequeue();

            //If the location of the current point corresponds to the end end the search.
            if (current == end) {
                break;
            }

            //Foreach neighbour of the current location perform the following.
            for (Tile next : map.getNeighbours(current)) {
                //Check whether the tile can be accessed.
                if (current.isAccessible(entity)) {
                    //Calculate the cost of moving to the next location.
                    //Currently 1 since the map will only return tiles which can be freely moved to.
                    double newCost = costSoFar.get(current) + next.getCost(entity);
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
        private final List<FrontierItem<T>> elements = new ArrayList<>();

        /**
         * Gets the size of the frontier.
         *
         * @return An integer that represents the size of the frontier.
         */
        int size() {
            return elements.size();
        }

        /**
         * Adds an item to the frontier.
         *
         * @param t        The item that should be added to the frontier.
         * @param priority The priority of the item.
         */
        void enqueue(T t, double priority) {
            elements.add(new FrontierItem<>(t, priority));
        }

        /**
         * Retrieves the item with the lowest priority.
         * Removes that item from the frontier.
         *
         * @return The item with the lowest priority.
         */
        T dequeue() {
            int bestIndex = 0;

            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).priority < elements.get(bestIndex).priority) {
                    bestIndex = i;
                }
            }

            T bestItem = elements.get(bestIndex).item;
            elements.remove(bestIndex);
            return bestItem;
        }

        /**
         * Class to capture a single item for the frontier.
         *
         * @param <X> The object of the frontier consists off.
         */
        private class FrontierItem<X> {
            final X item;
            final double priority;

            FrontierItem(X item, double priority) {
                this.item = item;
                this.priority = priority;
            }
        }
    }
}
