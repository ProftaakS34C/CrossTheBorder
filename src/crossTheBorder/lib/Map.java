package crossTheBorder.lib;

/**
 * Created by Oscar on 26-Sep-16.
 * The class map provides for the name , height and weight for the map.
 */
public class Map {
    private String name;
    private int width;
    private int height;

    /**
     * @param name   is the name of map
     * @param width  is the width of the map
     * @param height is the height of the map
     */

    public Map(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    /**
     * @return the Width of the map
     */
    public int getWidth() {
        return 0;
    }

    /**
     * @return the height of the map
     */
    public int getHeight() {
        return 0;
    }

}
