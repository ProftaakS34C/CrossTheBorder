package crosstheborder.lib;

import crosstheborder.lib.enumeration.ObstacleType;
import crosstheborder.lib.enumeration.TileType;
import crosstheborder.lib.interfaces.TileObject;
import crosstheborder.lib.tileobject.Obstacle;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Oscar de Leeuw
 */
public class MapLoader {
    private static final String ext = ".ctbmap";
    private static final HashMap<String, TileType> tileTypes = new HashMap<>();
    private static final HashMap<String, ObstacleType> obstacleTypes = new HashMap<>();
    private static MapLoader ourInstance = new MapLoader();

    private MapLoader() {
        obstacleTypes.put("x", null);
    }

    /**
     * Gets the singleton instance of the MapLoader class.
     *
     * @return The MapLoader instance.
     */
    public static MapLoader getInstance() {
        return ourInstance;
    }

    /**
     * Registers a new TileType with a certain code.
     *
     * @param code The code that is used in the .ctbmap encoding for this TileType.
     * @param type The type that is represented by the given code.
     */
    public void registerTileTypeCode(String code, TileType type) {
        if (!tileTypes.containsKey(code)) {
            tileTypes.put(code, type);
        }
    }

    /**
     * Registers a new ObstacleType with a certain code.
     *
     * @param code The code that is used in the .ctbmap encoding for this ObstacleType.
     * @param type The type that is represented by the given code.
     */
    public void registerObstacleTypeCode(String code, ObstacleType type) {
        if (!obstacleTypes.containsKey(code)) {
            obstacleTypes.put(code, type);
        }
    }

    public Map buildMap(String name) {
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
        Tile[][] tiles = getTilesFromLines(tileLines, objectLines, width, height);

        return new Map.Builder(name).setWidth(width).setHeight(height).setMexicoArea(mexicoArea).setUsaArea(usaArea).setTiles(tiles).build();
    }

    private File getMapFile(String name) {
        return new File("/maps/" + name + ".ctbmap");
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
            e.printStackTrace();
        }
    }

    private String getValue(String line) {
        return line.substring(line.indexOf("=") + 1, line.indexOf(";"));
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

    private Tile[][] getTilesFromLines(List<String> tiles, List<String> objects, int width, int height) {
        Tile[][] ret = new Tile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                char tileCode = tiles.get(i).charAt(j);
                char objectCode = objects.get(i).charAt(j);

                ret[i][j] = new Tile(tileTypes.get(tileCode));

                ObstacleType type = obstacleTypes.get(objectCode);

                if (type != null) {
                    TileObject obstacle = new Obstacle(type);
                    ret[i][j].setTileObject(obstacle);
                }
            }
        }

        return ret;
    }
}
