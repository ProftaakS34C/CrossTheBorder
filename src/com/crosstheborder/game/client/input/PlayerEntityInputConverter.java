package com.crosstheborder.game.client.input;

import com.sstengine.player.playerentity.MoveDirection;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oscar de Leeuw
 */
public class PlayerEntityInputConverter {
    private static Map<KeyCode, MoveDirection> inputActionKeys = new HashMap<KeyCode, MoveDirection>() {
        {
            put(KeyCode.A, MoveDirection.LEFT);
            put(KeyCode.D, MoveDirection.RIGHT);
            put(KeyCode.W, MoveDirection.UP);
            put(KeyCode.S, MoveDirection.DOWN);
        }
    };

    private static Map<KeyCode, UIAction> uiActionKeys = new HashMap<KeyCode, UIAction>() {
        {
            put(KeyCode.UP, UIAction.CAMERA_UP);
            put(KeyCode.DOWN, UIAction.CAMERA_DOWN);
            put(KeyCode.LEFT, UIAction.CAMERA_LEFT);
            put(KeyCode.RIGHT, UIAction.CAMERA_RIGHT);
        }
    };

    private PlayerEntityInputConverter() {
    }

    public static boolean isUIAction(KeyCode code) {
        return uiActionKeys.containsKey(code);
    }

    public static MoveDirection getMoveDirectionFromCode(KeyCode code) {
        return inputActionKeys.get(code);
    }

    public static UIAction getUIActionFromCode(KeyCode code) {
        return uiActionKeys.get(code);
    }
}
