package crosstheborder.client;

import crosstheborder.lib.enumeration.MoveDirection;
import javafx.scene.input.KeyCode;

import java.util.HashMap;

/**
 * @author Oscar de Leeuw
 */
public class InputConverter {
    private final static HashMap<KeyCode, MoveDirection> keyBindings = new HashMap<>();

    public InputConverter() {
        //TODO gather all this from a keybindings file.
        registerKeyBinding(KeyCode.A, MoveDirection.LEFT);
        registerKeyBinding(KeyCode.D, MoveDirection.RIGHT);
        registerKeyBinding(KeyCode.W, MoveDirection.UP);
        registerKeyBinding(KeyCode.S, MoveDirection.DOWN);
    }

    public void registerKeyBinding(KeyCode code, MoveDirection md) {
        keyBindings.put(code, md);
    }

    public MoveDirection getMoveDirectionFromKey(KeyCode keyCode) {
        MoveDirection ret = keyBindings.get(keyCode);
        if (ret == null) {
            return MoveDirection.NONE;
        }
        return ret;
    }
}
