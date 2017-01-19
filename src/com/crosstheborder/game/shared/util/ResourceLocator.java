package com.crosstheborder.game.shared.util;

import java.io.File;
import java.util.HashMap;

/**
 * Created by guill on 5-12-2016.
 */
public class ResourceLocator {
    private static final String imageDirectory = "images" + File.separator;
    private static final String imageExt = ".png";

    private static final HashMap<String, File> cache = new HashMap<>();

    private static File getFromCacheImage(String name) {
        if (cache.containsKey(name)) {
            return cache.get(name);
        } else {
            return loadImage(name);
        }
    }

    private static File loadImage(String name) {
        File file = new File(name);

        if (file.exists()) {
            cache.put(name, file);
            return file;
        } else {
            return new File(imageDirectory + "notexture" + imageExt);
        }
    }

    public static synchronized File getImage(String name) {
        String path = imageDirectory + name + imageExt;

        return getFromCacheImage(path);
    }
}
