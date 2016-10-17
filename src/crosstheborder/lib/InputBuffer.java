package crosstheborder.lib;

import crosstheborder.lib.enumeration.MoveDirection;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a buffer for inputMoves.
 * Handles what the users next desired action is going to be.
 * Saves up to a certain amount of inputs for movement at the same time and retrieves the oldest input first.
 * Thread safe.
 *
 * @author Oscar de Leeuw
 */
public class InputBuffer {
    private Deque<MoveDirection> inputMoves;
    //private InputPropertiesGetter properties = ServerSettings.getInstance();
    //private int bufferSize = properties.getInputBufferSize();
    private MoveDirection lastMove = MoveDirection.NONE;

    /**
     * Creates a new InputBuffer.
     */
    public InputBuffer() {
        inputMoves = new ArrayDeque<>();
    }

    /**
     * Synchronized method.
     * Adds a given {@link MoveDirection} to the input buffer.
     * If the buffer is full it will remove the oldest element and insert the new one.
     *
     * @param md The input that was given by the user.
     */
    public synchronized void addToInputMoves(MoveDirection md) {
        //Add method throws an illegal state exception when you can't add any more input,
        //(ab)using that to remove the oldest element since Java does not have a build in solution for this.
        inputMoves.offerFirst(md);
    }

    /**
     * Synchronized method.
     * Gets the oldest move in the input buffer.
     *
     * @return The oldest move in the input buffer.
     */
    public synchronized MoveDirection getNextInputMove() {
        MoveDirection currentMove = inputMoves.pollFirst();

        if (currentMove == null) {
            return MoveDirection.NONE;
        }

        //If there is another move in the buffer that is different from the last move, send that one instead.
        if (currentMove.equals(lastMove)) {
            List<MoveDirection> otherMoves = inputMoves.stream().filter(move -> !move.equals(lastMove)).collect(Collectors.toList());
            if (!otherMoves.isEmpty()) {
                currentMove = otherMoves.get(0);
            }
        }

        //Poll method gives back a null when there is no element, i.e. when there is no input.
        lastMove = currentMove;
        inputMoves.clear();
        return currentMove;
    }
}
