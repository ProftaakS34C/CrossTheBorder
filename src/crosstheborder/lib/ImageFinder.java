package crosstheborder.lib;

import crosstheborder.lib.enumeration.Country;
import crosstheborder.lib.enumeration.ObstacleType;
import crosstheborder.lib.enumeration.TileType;
import crosstheborder.lib.player.PlayerEntity;
import crosstheborder.lib.tileobject.Placeable;

import java.io.File;
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
     */
    public File getImage(ObstacleType type) {
        String name = type.name().toLowerCase();
        return getImage(name);
    }

    /**
     * Gets a file that contains the image.
     *
     * @param type The type of tile.
     * @return A file that contains the image.
     */
    public File getImage(TileType type) {
        String name = type.name().toLowerCase();
        return getImage(name);
    }

    /**
     * Gets a file that contains the image.
     *
     * @param placeable The placeable for which to get an image.
     * @return A file that contains the image.
     */
    public File getImage(Placeable placeable) {
        String name = placeable.getClass().getSimpleName().toLowerCase();
        return getImage(name);
    }

    /**
     * Gets a file that contains the image.
     *
     * @param entity The PlayerEntity for which to get an image.
     * @return A file that contains the image.
     */
    public File getImage(PlayerEntity entity) {
        String name = entity.getClass().getSimpleName().toLowerCase();
        return getImage(name);
    }

    public File getImage(Country country) {
        String name = country.name().toLowerCase();
        return getImage(name);
    }

    private File getImage(String name) {
        if (cache.containsKey(name)) {
            return cache.get(name);
        } else {
            return loadImage(name);
        }
    }

    private File loadImage(String name) {
        File file = new File(imageDirectory + name + ext);

        if (file.exists()) {
            cache.put(name, file);
            return file;
        } else {
            return new File(imageDirectory + "notexture" + ext);
        }
    }
}
