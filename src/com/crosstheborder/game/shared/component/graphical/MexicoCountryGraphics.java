package com.crosstheborder.game.shared.component.graphical;

import com.crosstheborder.game.shared.util.ResourceLocator;
import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.component.graphics.Painter;
import com.sstengine.country.Country;

import java.awt.*;
import java.io.File;

/**
 * Created by guill on 29-11-2016.
 */
public class MexicoCountryGraphics extends GraphicsComponent {
    @Override
    public void render(Object o, Painter painter, Point point, int width, int height) {
        painter.drawImage(ResourceLocator.getImage("mex"), point, width, height);
    }
}
