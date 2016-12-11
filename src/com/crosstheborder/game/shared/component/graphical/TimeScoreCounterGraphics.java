package com.crosstheborder.game.shared.component.graphical;

import com.crosstheborder.game.shared.ui.uiobjects.TimeScoreCounter;
import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;

import java.awt.*;

/**
 * @author Oscar de Leeuw
 */
public class TimeScoreCounterGraphics extends GraphicsComponent {
    @Override
    public void render(Object caller, Painter painter, Point location, int width, int height) {
        TimeScoreCounter counter = (TimeScoreCounter) caller;


    }
}
