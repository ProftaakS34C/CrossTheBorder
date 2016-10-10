package crosstheborder.lib.interfaces;

import java.awt.*;

/**
 * Drawable indicates that an object is drawable.
 *
 * @author Oscar de Leeuw
 */
@FunctionalInterface
public interface Drawable {
    /**
     * Draws the drawable.
     *
     * @param painter The Painter that can draw onto the UI.
     * @param location The pixel location the drawable should be painted.
     * @param tileWidth The width the drawable should be drawn with in pixels.
     */
    void draw(Painter painter, Point location, int tileWidth);
}
