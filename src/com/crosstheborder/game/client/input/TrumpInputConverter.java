package com.crosstheborder.game.client.input;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oscar de Leeuw
 */
public class TrumpInputConverter {
    private static Map<KeyCode, TrumpAction> trumpActionKeys = new HashMap<KeyCode, TrumpAction>() {
        {
            put(KeyCode.SPACE, TrumpAction.PLACE_OBSTACLE);
            put(KeyCode.Q, TrumpAction.SWITCH_TO_TRAP);
            put(KeyCode.E, TrumpAction.SWITCH_TO_WALL);
        }
    };

    private static Map<KeyCode, UIAction> uiActionKeys = new HashMap<KeyCode, UIAction>() {
        {
            put(KeyCode.UP, UIAction.CAMERA_UP);
            put(KeyCode.DOWN, UIAction.CAMERA_DOWN);
            put(KeyCode.LEFT, UIAction.CAMERA_LEFT);
            put(KeyCode.RIGHT, UIAction.CAMERA_RIGHT);
            put(KeyCode.A, UIAction.CAMERA_LEFT);
            put(KeyCode.D, UIAction.CAMERA_RIGHT);
            put(KeyCode.W, UIAction.CAMERA_UP);
            put(KeyCode.S, UIAction.CAMERA_DOWN);
        }
    };

    private TrumpInputConverter() {
    }

    public static boolean isUIAction(KeyCode code) {
        return uiActionKeys.containsKey(code);
    }

    public static UIAction getUIActionFromCode(KeyCode code) {
        return uiActionKeys.get(code);
    }

    public static TrumpAction getTrumpActionFromCode(KeyCode code) {
        return trumpActionKeys.get(code);
    }
}
