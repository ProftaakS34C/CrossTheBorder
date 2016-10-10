package crosstheborder.lib;

import crosstheborder.lib.enumeration.ObstacleType;
import crosstheborder.lib.enumeration.TileType;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.tileobject.Placeable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * @author Oscar de Leeuw
 */
public class ImageFinder {
    private static final String imageDirectory = "images/";
    private static final String ext = ".png";
    private static final HashMap<String, File> cache = new HashMap<>();
    private static ImageFinder ourInstance = new ImageFinder();

    private ImageFinder() {
    }

    /**
     * Gets the instance of ImageFinder.
     *
     * @return ImageFinder object.
     */
    public static ImageFinder getInstance() {
        return ourInstance;
    }

    /**
     * Gets a file that contains the image.
     *
     * @param type The type of obstacle.
     * @return A file that contains the image.
     * @throws FileNotFoundException When the file cannot be found.
     */
    public File getImage(ObstacleType type) throws FileNotFoundException {
        String name = type.name().toLowerCase();
        return getImage(name);
    }

    /**
     * Gets a file that contains the image.
     *
     * @param type The type of tile.
     * @return A file that contains the image.
     * @throws FileNotFoundException When the file cannot be found.
     */
    public File getImage(TileType type) throws FileNotFoundException {
        String name = type.name().toLowerCase();
        return getImage(name);
    }

    /**
     * Gets a file that contains the image.
     *
     * @param placeable The placeable for which to get an image.
     * @return A file that contains the image.
     * @throws FileNotFoundException When the file cannot be found.
     */
    public File getImage(Placeable placeable) throws FileNotFoundException {
        String name = placeable.getClass().getSimpleName().toLowerCase();
        return getImage(name);
    }

    /**
     * Gets a file that contains the image.
     *
     * @param entity The PlayerEntity for which to get an image.
     * @return A file that contains the image.
     * @throws FileNotFoundException When the file cannot be found.
     */
    public File getImage(PlayerEntity entity) throws FileNotFoundException {
        String name = entity.getClass().getSimpleName().toLowerCase();
        return getImage(name);
    }

    private File getImage(String name) throws FileNotFoundException {
        if (cache.containsKey(name)) {
            return cache.get(name);
        } else {
            return loadImage(name);
        }
    }

    private File loadImage(String name) throws FileNotFoundException {
        File file = new File(imageDirectory + name + ext);

        if (file.exists()) {
            return file;
        } else {
            throw new FileNotFoundException("Cant find image file for: " + name);
        }
    }
}
