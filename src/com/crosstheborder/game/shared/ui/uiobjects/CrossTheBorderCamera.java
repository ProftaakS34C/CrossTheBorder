package com.crosstheborder.game.shared.ui.uiobjects;

import com.crosstheborder.game.shared.component.graphical.uigraphics.CrossTheBorderCameraGraphics;
import com.sstengine.map.Map;
import com.sstengine.map.tile.Tile;
import com.sstengine.ui.UIObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The Camera class is a window into the map of the game.
 * The Camera can display a portion of the map around a given center point.
 * The Camera needs to live on an UI.
 *
 * @author Oscar de Leeuw
 */
public class CrossTheBorderCamera extends UIObject<Tile> {
    private static final Logger LOGGER = Logger.getLogger(CrossTheBorderCamera.class.getName());

    private Map map;
    private Point center;
    private int tileWidth;

    private int xTiles;
    private int yTiles;
    private int xStart;
    private int yStart;

    private List<Tile> currentTiles;

    /**
     * Creates a new camera.
     *
     * @param tileWidth    The width of the tiles in pixels..
     * @param location     The location where the Camera will be made.
     * @param cameraWidth  The width of the camera in pixels.
     * @param cameraHeight The height of the camera in pixels.
     * @param map          The map of the game.
     */
    public CrossTheBorderCamera(Map map, Point location, int cameraWidth, int cameraHeight, int tileWidth) {
        super(new CrossTheBorderCameraGraphics(), location, cameraWidth, cameraHeight);
        this.tileWidth = tileWidth;
        this.map = map;
        this.currentTiles = new ArrayList<>();

        setCenter(new Point(0, 0));
        resize(cameraWidth, cameraHeight);
    }

    /**
     * Gets the center of the camera.
     *
     * @return A point that represents the center of the camera.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Sets the center of the camera.
     * Recalculates the tiles the camera should display.
     *
     * @param center The new center of the camera.
     */
    public void setCenter(Point center) {
        this.center = center;

        recalculateCurrentTiles();
    }

    /**
     * Gets the width (and height) of a tile.
     *
     * @return The width of the tile in pixels.
     */
    public int getTileWidth() {
        return tileWidth;
    }

    /**
     * Gets a list of all the tiles that are currently within view of the camera.
     * Order of the list matters. The tiles are added in columns to the list.
     * <p>
     * It does NOT return a copy of the list and thus vulnerable to data manipulation.
     *
     * @return A list of all the tiles.
     */
    public List<Tile> getCurrentTiles() {
        return currentTiles;
    }

    private void recalculateCurrentTiles() {
        this.xStart = center.x - xTiles / 2; //The column of the left most tiles.
        this.yStart = center.y - yTiles / 2; //The row of the top most tiles.

        this.xStart = xStart < 0 ? 0 : xStart;
        this.yStart = yStart < 0 ? 0 : yStart;

        Tile[][] tiles = new Tile[0][];

        tiles = map.getAllTiles();

        currentTiles.clear();

        for (int x = xStart; x < xStart + xTiles && x < tiles.length; x++) {
            for (int y = yStart; y < yStart + yTiles && y < tiles[x].length; y++) {
                //Add the next tile to the list.
                currentTiles.add(tiles[x][y]);
            }
        }
    }

    public void refresh(Map map) {
        this.map = map;
        recalculateCurrentTiles();
    }

    /**
     * Resizes the camera.
     * Recalculates the tiles the camera should display.
     *
     * @param width  The new width of the camera in pixels.
     * @param height The new height of the camera in pixels.
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        xTiles = (int) Math.ceil((double) width / tileWidth); //The amount of tiles along the x-axis.
        yTiles = (int) Math.ceil((double) height / tileWidth); //The amount of tiles along the y-axis.

        recalculateCurrentTiles();
    }

    /**
     * Gets the location on the map from the camera of a certain pixel.
     * Requires the x and y coordinates to be set in relation to the x and y coordinates of the camera.
     *
     * @param x The x coordinate of the pixel, offset by the location of the camera on the UI.
     * @param y The y coordinate of the pixel, offset by the location of the camera on the UI.
     * @return A point that represents the location on the map.
     */
    @Override
    public Tile handleClick(int x, int y) {
        int xOffset = x / tileWidth;
        int yOffset = y / tileWidth;


        return map.getTile(xStart + xOffset, yStart + yOffset);
    }
}

