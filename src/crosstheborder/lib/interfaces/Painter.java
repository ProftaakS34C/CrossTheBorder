package crosstheborder.lib.interfaces;

import java.awt.*;

/**
 * Painter is an utility for the library to allow drawing of {@link Drawable}s.
 * Painter should be implemented in the UI.
 *
 * @author Oscar de Leeuw
 */
@FunctionalInterface
public interface Painter {
    /**
     * Draws an image onto the UI.
     *
     * @param image    The image that should be drawn onto the UI.
     * @param location The location at which the Image should be drawn.
     * @param width    The width of the image.
     * @param height   The height of the image.
     */
    void drawImage(Image image, Point location, int width, int height);
}
