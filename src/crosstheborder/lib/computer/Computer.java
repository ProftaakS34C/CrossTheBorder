package crosstheborder.lib.computer;

import crosstheborder.lib.Map;
import crosstheborder.lib.Tile;
import crosstheborder.lib.computer.algorithms.AStarAlgorithm;
import crosstheborder.lib.enumeration.Country;
import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.player.entity.BorderPatrol;
import crosstheborder.lib.player.entity.Mexican;

import java.awt.*;
import java.util.function.Predicate;

/**
 * @author Oscar de Leeuw
 */
public class Computer {
    private final static int COMPUTING_TIMEOUT = 5;
    private int timesComputed;

    private PlayerEntity entity;
    private Path path;
    private Predicate<Tile> goalPredicate;
    private Point goal; //TODO Somehow track what goal we are after. In the case of the borderPatrol this needs to be mexican.

    public Computer(PlayerEntity entity) {
        this.entity = entity;
        this.path = new Path(new AStarAlgorithm());
        setGoal();
    }

    /**
     * Computes a move for this computer.
     *
     * @param map
     */
    public void computeMove(Map map) {
        //Make sure that the recursive call is not called too much.
        if (timesComputed >= 5) {
            return;
        }
        timesComputed++;

        //Get the next location in the path.
        Point nextLocation = path.getNextLocation();
        Point currentLocation = entity.getLocation();

        //If the path is empty, look for a new goal.
        if (nextLocation == null) {
            path.calculateNewPath(currentLocation, goal, map);
            //Call this method again to calculate a move.
            computeMove(map);
        }

        //If the next location is not accessible then try to move around it.
        if (!map.isAccessible(nextLocation, entity)) {
            path.precedePath(currentLocation, map);
            computeMove(map);
        }

        //Make sure the target is still at the end of the path.
        if (!goal.equals(path.getEndPoint())) {
            //Recalculate the path to the goal.
            path.extendPath(goal, map);
        }

        //If the next location is accessible, push the input to the buffer.
        if (map.isAccessible(nextLocation, entity)) { //TODO make an isAccessible method.
            //Since nextLocation is not used again, use nextLocation to determine the movement direction.
            nextLocation.translate(-currentLocation.x, -currentLocation.y);
            MoveDirection md = MoveDirection.getMoveDirectionFromPoint(nextLocation);
            entity.pushInput(md);
        }
        //Else calculate a new path.
        else {
            path.calculateNewPath(currentLocation, goal, map);
        }

        timesComputed = 0;
    }

    //Checks whether the current path is still a good/speedy path to the goal.
    private boolean checkCurrentPath() {
        throw new UnsupportedOperationException(); //TODO
    }

    private Point findNewGoal(Map map) {
        throw new UnsupportedOperationException(); //TODO
    }

    private void setGoal() {
        if (entity instanceof Mexican) {
            goalPredicate = (tile -> tile.getCountry() == Country.USA); //TODO check whether the tile can be accessed.
        } else if (entity instanceof BorderPatrol) {
            goalPredicate = (tile -> tile.getPlayerEntity() instanceof Mexican && tile.getCountry() != Country.USA); //TODO make a general isAccessible check.
        }
    }

}
