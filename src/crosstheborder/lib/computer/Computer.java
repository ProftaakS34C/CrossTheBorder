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
//TODO refactor this whole mess into nice little methods.

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
        //If the possible targets have never been set or is empty, reset the computer.
        if (possibleTargets == null || possibleTargets.isEmpty()) {
            resetComputer(map);
            return;
        }

        //Make sure that the recursive call is not called too much.
        //Also check whether the computer can actually move.
        if (timesComputed >= COMPUTING_TIMEOUT || !entity.canMove()) {
            return;
        }
        timesComputed++;

        //Get the next location in the path.
        Tile nextTile = path.getNextLocation();

        //If the path is empty, there is a target closer, create a path.
        if (nextTile == null || !verifyClosestTargetIsGoal() || !nextTile.isAccessible(entity)) {
            if (getNewPath(map)) {
                //Call this method again to calculate a move.
                computeMove(map);
            }
            return;
        }

        //If the next location is not accessible then try to move around it.
        /*if (!nextTile.isAccessible(entity)) {
            path.precedePath(currentTile, map, entity);
            computeMove(map);
            return;
        }

        //Make sure the target is still at the end of the path.
        if (!verifyPathEndsInGoal(path.getEndTile())) {
            //Recalculate the path to the goal.
            path.extendPath(currentTile, currentTarget.getTile(), map, entity);
        }*/

        //If the next location is accessible, push the input to the buffer.
        if (nextTile.isAccessible(entity)) {
            moveTo(nextTile.getLocation());
        }

        timesComputed = 0;
        //Age the path so are aware of the age of the path.
        path.age();
    }

    /**
     * Pushes input to the input buffer.
     *
     * @param nextLoc The location to move to next.
     */
    private void moveTo(Point nextLoc) {
        Point currentLoc = entity.getLocation();

        //Since nextLocation is not used again, use nextLocation to determine the movement direction.
        Point nextLocation = new Point(nextLoc.x - currentLoc.x, nextLoc.y - currentLoc.y);
        MoveDirection md = MoveDirection.getMoveDirectionFromPoint(nextLocation);
        entity.pushInput(md);
    }

    /**
     * Checks whether the end of the path is still the same as the location of the target.
     *
     * @param endOfPath The end of the path.
     * @return True when the end of the path coincides with the current target.
     */
    private boolean verifyPathEndsInGoal(Tile endOfPath) {
        if (endOfPath != currentTarget.getTile()) {
            return false;
        }
        return true;
    }

    /**
     * Verifies that a goal can be reached by this entity.
     *
     * @param goal The goal.
     * @return True when the goal can be reached.
     */
    private boolean verifyGoalCanBeReached(Tile goal) {
        return goal.isAccessible(entity);
    }

    /**
     * Verifies that the current target the closest target.
     *
     * @return True when the closest target is the current target.
     */
    private boolean verifyClosestTargetIsGoal() {
        if (findClosestTarget() != currentTarget) {
            return false;
        }
        return true;
    }

    /**
     * Finds the closest target that is accessible to the computer as defined by the heuristic function.
     */
    private Interactable findClosestTarget() {
        //Get only the targets that are accessible.
        List<Interactable> targets = possibleTargets.stream().filter(x -> x.getTile().isAccessible(entity)).collect(Collectors.toList());
        //Sort the list according to the heuristics.
        targets.sort(goalHeuristic);

        if (!targets.isEmpty()) {
            return targets.get(0);
        }
        return null;
    }

    private boolean getNewPath(Map map) {
        currentTarget = findClosestTarget();
        if (currentTarget == null) {
            return false;
        }

        Tile goal = currentTarget.getTile();
        Tile currentLocation = entity.getTile();

        if (verifyGoalCanBeReached(goal)) {
            path.calculateNewPath(currentLocation, goal, map, entity);
            return true;
        }
        return false;
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
            goalPredicate = (tile -> tile.getCountry() == Country.USA);
            goalHeuristic = ((x, y) -> ((Double) (currentLoc.distance(x.getTile().getLocation()))).compareTo(currentLoc.distance(y.getTile().getLocation())));

            //Get all the tiles that satisfy the predicate.
            possibleGoals = map.getTiles(goalPredicate);
            possibleTargets.addAll(possibleGoals.stream().map(Tile::getCountry).collect(Collectors.toList()));

        } else if (entity.getTeam().getCountry() == Country.USA) {
            goalPredicate = (tile -> tile.hasPlayerEntity() && tile.getPlayerEntity().getTeam().getCountry() == Country.MEX);
            goalHeuristic = ((x, y) -> ((Double) (currentLoc.distance(x.getTile().getLocation()))).compareTo(currentLoc.distance(y.getTile().getLocation())));

            //Get all the tiles that satisfy the predicate.
            possibleGoals = map.getTiles(goalPredicate);
            possibleTargets.addAll(possibleGoals.stream().map(Tile::getPlayerEntity).collect(Collectors.toList()));
        }

        currentTarget = findClosestTarget();
        //TODO country cannot be a valid target as it is an enum and enums are static, thus all tiles are the same.
    }

}
