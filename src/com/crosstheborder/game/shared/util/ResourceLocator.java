package com.crosstheborder.game.shared.util;

import java.io.File;

/**
 * Created by guill on 5-12-2016.
 */
public class ResourceLocator {


    public static synchronized File getImage(String name) {
        String map =  File.separator + "images" + File.separator;
        String ext = ".png";

        String path = map + name + ext;

        return new File(path);
    }
}
