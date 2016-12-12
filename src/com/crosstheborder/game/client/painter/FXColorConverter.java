package com.crosstheborder.game.client.painter;

import javafx.scene.paint.Color;

/**
 * @author Oscar de Leeuw
 */
public class FXColorConverter {

    public static Color getColor(java.awt.Color color) {
        return Color.rgb(color.getRed(), color.getGreen(), color.getBlue(), ((double) color.getAlpha()) / 255.0);
    }
}
