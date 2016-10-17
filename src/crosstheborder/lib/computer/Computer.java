package crosstheborder.lib.computer;

import crosstheborder.lib.Map;
import crosstheborder.lib.Tile;
import crosstheborder.lib.computer.algorithms.AStarAlgorithm;
import crosstheborder.lib.enumeration.Country;
import crosstheborder.lib.enumeration.MoveDirection;
import crosstheborder.lib.interfaces.Interactable;
import crosstheborder.lib.player.PlayerEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//TODO make sure computers can climb a wall. Or rather, know that a certain move is going to cost more and thus repeat the move.

/**
 * @author Oscar de Leeuw
 */
public class Computer {
    private final static int COMPUTING_TIMEOUT = 5;
    private int timesComputed;

    private PlayerEntity entity;
    private Path path;

    private Predicate<Tile> goalPredicate;
    private Comparator<Interactable> goalHeuristic;

    private List<Interactable> possibleTargets;
    private Interactable currentTarget;

    public Computer(PlayerEntity entity) {
        this.entity = entity;
        this.path = new Path(new AStarAlgorithm());
    }

    /**
     * Computes a move for this computer.
     *
     * @param map The map on which to compute a move.
     */
    public void computeMove(Map map) {
        //If the possible targets have never been set, set them now.
        if (possibleTargets == null) {
            resetComputer(map);
            computeMove(map);
        }

        //Make sure that the recursive call is not called too much.
        //Also check whether the computer can actually move.
        if (timesComputed >= COMPUTING_TIMEOUT || !entity.canMove()) {
            return;
        }
        timesComputed++;

        //Get the next location in the path.
        Tile nextTile = path.getNextLocation();
        Tile currentTile = entity.getTile();
        //Find the closest target.
        Interactable closestTarget = findClosestTarget();

        //If the path is empty or there is a target closer, create a path to that.
        if (nextTile == null || closestTarget != currentTarget) {
            currentTarget = closestTarget;
            path.calculateNewPath(currentTile, currentTarget.getTile(), map, entity);
            //Call this method again to calculate a move.
            computeMove(map);
        }

        //If the next location is not accessible then try to move around it.
        if (nextTile.isAccessible(entity)) {
            path.precedePath(currentTile, map, entity);
            computeMove(map);
        }

        //Make sure the target is still at the end of the path.
        if (!checkTarget(path.getEndTile())) {
            //Recalculate the path to the goal.
            path.extendPath(currentTile, currentTarget.getTile(), map, entity);
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
            path.calculateNewPath(currentTile, currentTarget.getTile(), map, entity);
            computeMove(map);
        }

        timesComputed = 0;
        //Age the path so are aware of the age of the path.
        path.age();
    }

    /**
     * Checks whether the end of the path is still the same as the location of the target.
     *
     * @param endOfPath The end of the path.
     * @return True when the end of the path coincides with the current target.
     */
    private boolean checkTarget(Tile endOfPath) {
        if (endOfPath != currentTarget.getTile()) {
            return false;
        }
        return true;
    }

    /**
     * Finds the closest target to the computer as defined by the heuristic function.
     */
    private Interactable findClosestTarget() {
        possibleTargets.sort(goalHeuristic);
        return possibleTargets.get(0);
    }

    /**
     * Resets all the possible targets of the computer.
     * Requires the map of the game in order to acquire targets.
     *
     * @param map The map of the game.
     */
    public void resetComputer(Map map) {
        List<Tile> possibleGoals;
        possibleTargets = new ArrayList<>();
        Point currentLoc = entity.getLocation();

        if (entity.getTeam().getCountry() == Country.MEX) {
            goalPredicate = (tile -> tile.getCountry() == Country.USA && tile.isAccessible(entity));
            goalHeuristic = ((x, y) -> ((Double) (currentLoc.distance(x.getTile().getLocation()))).compareTo(currentLoc.distance(y.getTile().getLocation())));

            //Get all the tiles that satisfy the predicate.
            possibleGoals = map.getTiles(goalPredicate);
            possibleTargets.addAll(possibleGoals.stream().map(Tile::getCountry).collect(Collectors.toList()));

        } else if (entity.getTeam().getCountry() == Country.USA) {
            goalPredicate = (tile -> tile.getPlayerEntity().getTeam().getCountry() == Country.MEX && tile.isAccessible(entity));
            goalHeuristic = ((x, y) -> ((Double) (currentLoc.distance(x.getTile().getLocation()))).compareTo(currentLoc.distance(y.getTile().getLocation())));

            //Get all the tiles that satisfy the predicate.
            possibleGoals = map.getTiles(goalPredicate);
            possibleTargets.addAll(possibleGoals.stream().map(Tile::getPlayerEntity).collect(Collectors.toList()));
        }

        currentTarget = findClosestTarget();
    }

}
