package crossTheBorder.lib;

import java.awt.*;

/**
 * Represents an object on a tile
 * @author Joram
 * @version 1.0
 */
public class TileObject {
    private Image image;

    /**
     * This is the constructor method of the class "User"
     * in the constructor the image of the TileObject is set
     * @param image the image of the TileObject
     */
    public TileObject(Image image){
        this.image = image;
    }

    /**
     * This method is used to get the image of the TileObject
     * @return Image the image of the TileObject
     */
    public Image getImage(){
        return image;
    }
}
