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

        //draw the victory flag on the screen
        painter.drawImage(screen.getVictoryFlagFile(), location, width, height);

        //draw the victory text on the screen
        Point victoryTextPoint = new Point((width*3)/10, (height*4)/10);
        painter.drawString(screen.getVictoryText(),victoryTextPoint, (width*6)/10, (height*2)/10, Color.YELLOW, true);

        //draw the score on the screen
        Point scorePoint = new Point((width*2)/10, (height*6)/10);
        int mexScore = screen.getMexScore();
        int usaScore = screen.getUsaScore();
        String score = "Mex: "+mexScore+" VS Usa: "+usaScore;
        painter.drawString(score, scorePoint, (width*6)/10, (height*2)/10, Color.YELLOW, true);

        //draw the continue button on the screen
        Point buttonPoint = new Point((width*2)/10, (height*7)/10);

        painter.drawRectangle(buttonPoint, (width*4)/10, (height*2)/10, Color.black, false);
        painter.drawString("continue", new Point(buttonPoint.x, buttonPoint.y + 100), (width*4)/10, (height*2)/10, Color.YELLOW,true);
    }

}
