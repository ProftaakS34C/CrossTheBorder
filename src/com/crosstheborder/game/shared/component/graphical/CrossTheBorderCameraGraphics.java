package com.crosstheborder.game.shared.component.graphical;

import com.crosstheborder.game.shared.ui.uiobjects.CrossTheBorderCamera;
import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;
import com.sstengine.map.tile.Tile;
import com.sstengine.ui.uiobjects.Camera;

import java.awt.*;

/**
 * The CameraGraphics is a standard GraphicsComponent for the {@link Camera} class.
 * It handles the rendering of the tiles of the camera on the painter.
 * Should only be used by a Camera class, using any other class will result in a runtime InvalidCastException.
 *
 * @author Oscar de Leeuw
 */
public class CrossTheBorderCameraGraphics extends GraphicsComponent {
    @Override
    public void render(Object caller, Painter painter, Point location, int width, int height) {
        CrossTheBorderCamera camera = (CrossTheBorderCamera) caller;
        int tileWidth = camera.getTileWidth();

        int initX = location.x;
        int initY = location.y;
        Point start = new Point(initX, initY); //The current pixel location at which the image should be drawn.

        //Since the tiles are all added in a certain order to the currentTiles list, we can just loop through them again.
        for (Tile tile : camera.getCurrentTiles()) {
            //Render the tile.
            tile.render(painter, start, tileWidth, tileWidth);
            //Move to the next tile.
            start.translate(0, tileWidth);

            //If we have done one column of tiles, shift one to the right.
            if (start.y >= height + initY) {
                start.translate(tileWidth, -start.y);
            }
        }
    }
}


