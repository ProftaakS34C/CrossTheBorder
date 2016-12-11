package com.crosstheborder.game.shared.util;


import com.crosstheborder.game.shared.factory.ObstacleFactory;
import com.crosstheborder.game.shared.factory.TileFactory;
import com.sstengine.country.Country;
import com.sstengine.map.Map;
import com.sstengine.map.tile.Tile;
import com.sstengine.obstacle.Obstacle;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton that contains code for loading a map.
 *
 * @author Oscar de Leeuw
 */
public class CrossTheBorderMapLoader {
    private static final Logger LOGGER = Logger.getLogger(CrossTheBorderMapLoader.class.getName());

    private static final String directory = "maps/";
    private static final String ext = ".ctbmap";
    private static final HashMap<Character, CrossTheBorderTileType> tileTypes = new HashMap<>();
    private static final HashMap<Character, CrossTheBorderStaticObstacleType> obstacleTypes = new HashMap<>();
    private static CrossTheBorderMapLoader ourInstance = new CrossTheBorderMapLoader();

    private CrossTheBorderMapLoader() {
        obstacleTypes.put('x', null);

        for (CrossTheBorderStaticObstacleType type : CrossTheBorderStaticObstacleType.values()) {
            registerObstacleTypeCode(type.getCode(), type);
        }

        for (CrossTheBorderTileType type : CrossTheBorderTileType.values()) {
            registerTileTypeCode(type.getCode(), type);
        }
    }

    /**
     * Gets the singleton instance of the MapLoader class.
     *
     * @return The MapLoader instance.
     */
    public static CrossTheBorderMapLoader getInstance() {
        return ourInstance;
    }

    /**
     * Registers a new TileType with a certain code.
     *  @param code The code that is used in the .ctbmap encoding for this TileType.
     * @param type The type that is represented by the given code.
     */
    private void registerTileTypeCode(char code, CrossTheBorderTileType type) {
        if (!tileTypes.containsKey(code)) {
            tileTypes.put(code, type);
        }
    }

    /**
     * Registers a new ObstacleType with a certain code.
     *  @param code The code that is used in the .ctbmap encoding for this ObstacleType.
     * @param type The type that is represented by the given code.
     */
    private void registerObstacleTypeCode(char code, CrossTheBorderStaticObstacleType type) {
        if (!obstacleTypes.containsKey(code)) {
            obstacleTypes.put(code, type);
        }
    }

    /**
     * Builds a map with the .ctbmap format from the given name.
     * Assumes all maps are in a map called maps in the root folder of the program.
     * Uses the name of the map as filename.
     *
     * @param name The name of the map.
     * @return A map object.
     */
    public Map buildMap(String name, Country usa, Country mex, ObstacleFactory obstacleFactory, TileFactory tileFactory) {
        File file = getMapFile(name);

        StringBuilder widthLine = new StringBuilder();  //width=x
        StringBuilder heightLine = new StringBuilder(); //height=x
        StringBuilder usaAreaLine = new StringBuilder();    //usaArea=(x,y,width,height)
        StringBuilder mexicoAreaLine = new StringBuilder(); //mexicoArea=(x,y,width,height)
        List<String> tileLines = new ArrayList<>();
        List<String> objectLines = new ArrayList<>();

        readFile(file, widthLine, heightLine, usaAreaLine, mexicoAreaLine, tileLines, objectLines);

        int width = getIntFromLine(widthLine.toString());
        int height = getIntFromLine(heightLine.toString());
        Rectangle usaArea = getAreaFromLine(usaAreaLine.toString());
        Rectangle mexicoArea = getAreaFromLine(mexicoAreaLine.toString());
        Tile[][] tiles = getTilesFromLines(tileLines, objectLines, width, height, usaArea, mexicoArea, usa, mex, obstacleFactory, tileFactory);

        return new Map.Builder(name).setWidth(width).setHeight(height).setTiles(tiles).build();
    }

    private File getMapFile(String name) {
        return new File(directory + name + ext);
    }

    private void readFile(File file, StringBuilder widthLine, StringBuilder heightLine, StringBuilder usaArea, StringBuilder mexicoArea, List<String> tiles, List<String> tileObjects) {
        boolean readingTiles = false;
        boolean readingTileObjects = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line; (line = br.readLine()) != null; ) {
                //If the current lines represent tiles:
                if (readingTiles) {
                    if (line.contains(";")) {
                        readingTiles = false;
                    } else {
                        tiles.add(line);
                    }
                }
                //Else if the current lines represent tile objects:
                else if (readingTileObjects) {
                    if (line.contains(";")) {
                        readingTiles = false;
                    } else {
                        tileObjects.add(line);
                    }
                } else if (line.startsWith("width=")) {
                    widthLine.append(line);
                } else if (line.startsWith("height=")) {
                    heightLine.append(line);
                } else if (line.startsWith("usaArea=")) {
                    usaArea.append(line);
                } else if (line.startsWith("mexicoArea=")) {
                    mexicoArea.append(line);
                } else {
                    readingTiles = line.startsWith("tiles=");
                    readingTileObjects = line.startsWith("objects=");
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private String getValue(String line) {
        return line.substring(line.indexOf('=') + 1, line.indexOf(';'));
    }

    private int getIntFromLine(String line) {
        return Integer.parseInt(getValue(line));
    }

    private Rectangle getAreaFromLine(String line) {
        String[] temp = getValue(line).split(",");
        int[] values = new int[temp.length];

        for (int i = 0; i < values.length; i++) {
            values[i] = Integer.parseInt(temp[i]);
        }

        return new Rectangle(values[0], values[1], values[2], values[3]);
    }

    private Tile[][] getTilesFromLines(List<String> tiles, List<String> objects, int width, int height, Rectangle usaRect, Rectangle mexico, Country usa, Country mex, ObstacleFactory obstacleFactory, TileFactory tileFactory) {
        Tile[][] ret = new Tile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                char tileCode = tiles.get(j).charAt(i);
                char objectCode = objects.get(j).charAt(i);
                Country country;
                Obstacle obstacle = null;

                CrossTheBorderStaticObstacleType obstacleType = obstacleTypes.get(objectCode);
                CrossTheBorderTileType tileType = tileTypes.get(tileCode);

                if (obstacleType != null) {
                    obstacle = obstacleFactory.createStaticObstacle(obstacleType);
                }

                if (usaRect.contains(i, j) && obstacle == null) {
                    country = usa;
                } else if (mexico.contains(i, j) && obstacle == null) {
                    country = mex;
                } else {
                    country = null;
                }

                int id = j + (i*j);

                ret[i][j] = tileFactory.createTile(id, tileType, new Point(i,j));
                ret[i][j].setObstacle(obstacle);

                if (country != null) {
                    country.addLand(ret[i][j]);
                }
            }
        }

        return ret;
    }
}
