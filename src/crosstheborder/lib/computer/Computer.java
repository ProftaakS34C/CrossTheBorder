package crosstheborder.lib.computer;

import crosstheborder.lib.Map;
import crosstheborder.lib.Tile;
import crosstheborder.lib.computer.algorithms.AStarAlgorithm;
import crosstheborder.lib.enumeration.Country;
import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.player.PlayerEntity;

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
    private Tile goal; //TODO Somehow track what goal we are after. In the case of the borderPatrol this needs to be mexican.

    public Computer(PlayerEntity entity) {
        this.entity = entity;
        this.path = new Path(new AStarAlgorithm());
        setGoal();
    }

    /**
     * Computes a move for this computer.
     *
     * @param map The map on which to compute a move.
     */
    public void computeMove(Map map) {
        //Make sure that the recursive call is not called too much.
        //Also check whether the computer can actually move.
        if (timesComputed >= 5 || !entity.canMove()) {
            return;
        }
        timesComputed++;

        //Get the next location in the path.
        Tile nextTile = path.getNextLocation();
        Tile currentTile = entity.getTile();

        //If the path is empty, look for a new goal.
        if (nextTile == null) {
            findNewGoal(map);
            path.calculateNewPath(currentTile, goal, map, entity);
            //Call this method again to calculate a move.
            computeMove(map);
        }

        //If the next location is not accessible then try to move around it.
        if (nextTile.isAccessible(entity)) {
            path.precedePath(currentTile, map, entity);
            computeMove(map);
        }

        //Make sure the target is still at the end of the path.
        if (!goal.equals(path.getEndTile())) {
            //Recalculate the path to the goal.
            path.extendPath(goal, map, entity);
        }

        //If the next location is accessible, push the input to the buffer.
        if (nextTile.isAccessible(entity)) {
            //Since nextLocation is not used again, use nextLocation to determine the movement direction.
            Point nextLocation = new Point(nextTile.getLocation().x - currentTile.getLocation().x, nextTile.getLocation().x - currentTile.getLocation().y);
            MoveDirection md = MoveDirection.getMoveDirectionFromPoint(nextLocation);
            entity.pushInput(md);
        }
        //Else calculate a new path.
        else {
            path.calculateNewPath(currentTile, goal, map, entity);
            computeMove(map);
        }

        timesComputed = 0;
        //Age the path so are aware of the age of the path.
        path.age();
    }

    //Checks whether the current path is still a good/speedy path to the goal.
    private boolean checkCurrentPath() {
        throw new UnsupportedOperationException(); //TODO
    }

    private void findNewGoal(Map map) {
        throw new UnsupportedOperationException(); //TODO
    }

    private void setGoal() {
        if (entity.getTeam().getCountry() == Country.MEX) {
            goalPredicate = (tile -> tile.getCountry() == Country.USA && tile.isAccessible(entity));
        } else if (entity.getTeam().getCountry() == Country.USA) {
            goalPredicate = (tile -> tile.getPlayerEntity().getTeam().getCountry() == Country.MEX && tile.isAccessible(entity));
        }
    }

}
