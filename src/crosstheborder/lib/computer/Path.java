package crosstheborder.lib.computer;

import crosstheborder.lib.Map;
import crosstheborder.lib.Tile;
import crosstheborder.lib.player.PlayerEntity;

import java.util.Deque;

/**
 * @author Oscar de Leeuw
 */
class Path {
    private PathingAlgorithm algorithm;
    private Deque<Tile> path;
    private int age;

    /**
     * Creates a new Path object with the given algorithm.
     *
     * @param algorithm The pathing algorithm that should be used for pathing.
     */
    Path(PathingAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Gets the next location on the path.
     * Increases the age of the path by 1.
     *
     * @return The next tile in the path.
     */
    Tile getNextLocation() {
        return path != null ? path.pollFirst() : null;
    }

    /**
     * Gets, but doesn't remove, the goal of the path.
     *
     * @return The endpoint of the path.
     */
    Tile getEndTile() {
        return path != null ? path.peekLast() : null;
    }

    /**
     * Gets the size of the path.
     *
     * @return The amount of tiles in the path.
     */
    int size() {
        return this.path.size();
    }

    /**
     * Returns the age of the path.
     *
     * @return The age of the path in server ticks.
     */
    int getAge() {
        return this.age;
    }

    /**
     * Increases the age of the tile.
     */
    void age() {
        this.age++;
    }

    /**
     * Checks whether a given tile is present in the path.
     *
     * @param tile The point to check for.
     * @return True when the point is present in the path.
     */
    public boolean contains(Tile tile) {
        return path.contains(tile);
    }

    /**
     * Calculates a new path to the given goal.
     * Path excludes the start. Path includes the goal.
     * Resets the age of the path.
     *
     * @param start The starting location of the path. Generally the current location of the entity.
     * @param goal  The end of the path. Generally the goal of the entity.
     * @param map   The map on which the path should be calculated.
     * @param entity The entity for which to calculate a path.
     */
    void calculateNewPath(Tile start, Tile goal, Map map, PlayerEntity entity) {
        this.path = algorithm.calculatePath(map, entity, start, goal);
        age = 0;
    }

    /**
     * Connects a given start to the current path.
     * Polls the first element to make place for the goal from the new calculation.
     *
     * @param start The new start of the path.
     * @param map   The map on which the path should be calculated.
     * @param entity The entity for which to calculate a path.
     */
    void precedePath(Tile start, Map map, PlayerEntity entity) {
        //Must poll since the goal is going to be added to the map again.
        Tile goal = path.pollFirst();
        //Calculate a new path.
        Deque<Tile> extraPath = algorithm.calculatePath(map, entity, start, goal);
        age += extraPath.size();

        //ExtraPath will be reduced in size every iteration.
        int size = extraPath.size();
        //Adds the extraPath in correct order on top of the path.
        for (int i = 0; i < size; i++) {
            //Get the last point in the extra path.
            Tile point = extraPath.pollLast();
            //Push it to the top of the current path.
            path.offerFirst(point);
        }
    }

    /**
     * Extends the path to a new goal.
     * Will remove an amount of tiles equal to the age of the path before recalculating the path in order to guaranteed the best path.
     *
     * @param start The current location of the entity, might be needed if the path is too old.
     * @param goal The goal of the extension.
     * @param map  The map on which the path should be calculated.
     * @param entity The entity for which to calculate a path.
     */
    void extendPath(Tile start, Tile goal, Map map, PlayerEntity entity) {
        //If the path is too old anyway calculate a new path.
        if (age >= path.size()) {
            calculateNewPath(start, goal, map, entity);
            return;
        }

        for (int i = 0; i < age; i++) {
            path.pollLast();
        }
        Tile endOfOldPath = path.peekLast();
        Deque<Tile> extraPath = algorithm.calculatePath(map, entity, endOfOldPath, goal);

        //ExtraPath will be reduced in size every iteration.
        int size = extraPath.size();
        //Adds the extraPath in correct order at the bottom of the path.
        for (int i = 0; i < size; i++) {
            //Get the first point in the extra path.
            Tile point = extraPath.pollFirst();
            //Push it to the end of the path.
            path.offerLast(point);
        }
    }
}
