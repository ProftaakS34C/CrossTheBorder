package crosstheborder.ui.input;

import crosstheborder.lib.enumeration.MoveDirection;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Represents a buffer for inputMoves.
 * Handles what the users next desired action is going to be.
 * Saves up to 10 inputs at the same time and retrieves the oldest input first.
 * Thread safe.
 *
 * @author Oscar de Leeuw
 */
public class InputBuffer {
    private Queue<MoveDirection> inputMoves;

    /**
     * Creates a new InputBuffer.
     */
    public InputBuffer() {
        inputMoves = new ArrayBlockingQueue<>(10);
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
        try {
            inputMoves.add(md);
        } catch (IllegalStateException ex) {
            inputMoves.poll();
            inputMoves.add(md);
        }
    }

    /**
     * Synchronized method.
     * Gets the oldest move in the input buffer.
     *
     * @return The oldest move in the input buffer.
     */
    public synchronized MoveDirection getNextInputMove() {
        MoveDirection ret = inputMoves.poll();

        //Poll method gives back a null when there is no element, i.e. when there is no input.
        return ret != null ? ret : MoveDirection.NONE;
    }
}
