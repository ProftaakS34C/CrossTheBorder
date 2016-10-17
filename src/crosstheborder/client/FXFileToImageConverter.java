package crosstheborder.client;


import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @author Oscar de Leeuw
 */
public class FXFileToImageConverter {
    private final HashMap<File, Image> cache = new HashMap<>();

    public Image getImage(File file) {
        Image ret = cache.get(file);
        if (ret == null) {
            return loadImage(file);
        }
        return ret;
    }

    private Image loadImage(File file) {
        try (InputStream is = new FileInputStream(file)) {
            Image img = new Image(is);
            cache.put(file, img);
            return img;
        } catch (IOException e) {
            return new WritableImage(1, 1); //TODO make sure this is not magical.
        }
    }
}
