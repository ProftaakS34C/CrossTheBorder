package com.crosstheborder.game.shared.component.graphical.uigraphics;

import com.crosstheborder.game.shared.ui.uiobjects.TimeScoreCounter;
import com.crosstheborder.game.shared.util.ResourceLocator;
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

        Point usaScoreLoc = location;
        int usaWidth = (width * 3) / 10;

        Point timeLoc = new Point(usaScoreLoc.x + usaWidth, usaScoreLoc.y);
        int timeWidth = (width * 4) / 10;

        Point mexScoreLoc = new Point(timeLoc.x + timeWidth, usaScoreLoc.y);
        int mexWidth = usaWidth;

        //painter.drawRectangle(usaScoreLoc, usaWidth, height, new Color(0, 0, 255, 100), true);
        painter.drawImage(ResourceLocator.getImage("usa"), usaScoreLoc, usaWidth, height);
        painter.drawRectangle(timeLoc, timeWidth, height, Color.BLUE, false);
        painter.drawImage(ResourceLocator.getImage("mex"), mexScoreLoc, mexWidth, height);
        //painter.drawRectangle(mexScoreLoc, mexWidth, height, new Color(0, 255, 0, 100), true);

        painter.drawString(counter.remainingTime(), new Point(timeLoc.x + timeWidth / 6, timeLoc.y + height - 10), timeWidth, height, Color.WHITE, true);
        painter.drawString(counter.scoreMEX() + "", new Point(mexScoreLoc.x + mexWidth / 3, mexScoreLoc.y + height - 10), mexWidth, height, Color.WHITE, true);
        painter.drawString(counter.scoreUSA() + "", new Point(usaScoreLoc.x + mexWidth / 3, usaScoreLoc.y + height - 10), usaWidth, height, Color.WHITE, true);
    }
}
