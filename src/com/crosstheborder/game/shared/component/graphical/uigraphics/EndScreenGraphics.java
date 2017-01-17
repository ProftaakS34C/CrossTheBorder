package com.crosstheborder.game.shared.component.graphical.uigraphics;

import com.crosstheborder.game.shared.ui.EndScreen;
import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;

import java.awt.*;

/**
 * Created by yannic on 17/01/2017.
 */
public class EndScreenGraphics extends GraphicsComponent {
    @Override
    public void render(Object caller, Painter painter, Point location, int width, int height) {
        EndScreen screen = (EndScreen) caller;

        //test
        painter.drawString("DIT IS EEN TEST", new Point(5, 5), width, height, Color.CYAN, true);
        //draw the victory flag on the screen
        painter.drawImage(screen.getVictoryFlagFile(), location, width, height);

        //draw the victory text on the screen

        //draw the score on the screen

        //draw the continue button on the screen

    }

}
