package crosstheborder.lib.enumeration;

import crosstheborder.lib.interfaces.Drawable;
import crosstheborder.lib.interfaces.Painter;

import java.awt.*;

/**
 * @author Oscar de Leeuw
 */
public enum Country implements Drawable {
    USA, MEX, NONE;


    @Override
    public void draw(Painter painter, Point location, int tileWidth) {
        //TODO Add a draw method for painter.
    }
}
