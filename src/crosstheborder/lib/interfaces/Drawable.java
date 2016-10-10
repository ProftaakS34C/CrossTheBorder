package crosstheborder.lib.interfaces;

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
     */
    void draw(Painter painter);
}
