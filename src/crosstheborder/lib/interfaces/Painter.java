package crosstheborder.lib.interfaces;

import java.awt.*;
import java.io.File;

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
     * @param file     The file that contains the image.
     * @param location The location at which the Image should be drawn.
     * @param width    The width of the image.
     * @param height   The height of the image.
     */
    void drawImage(File file, Point location, int width, int height);
}
