package com.crosstheborder.game.shared.ui.uiobjects;

import com.crosstheborder.game.shared.component.graphical.uigraphics.CenterMarkerGraphics;
import com.sstengine.ui.UIObject;

import java.awt.*;

/**
 * @author Oscar de Leeuw
 */
public class CenterMarker extends UIObject {

    public CenterMarker(Rectangle area, int priority) {
        super(new CenterMarkerGraphics(), area, priority);
    }

    public CenterMarker(Rectangle area) {
        super(new CenterMarkerGraphics(), area);
    }

    public CenterMarker(Point location, int width, int height) {
        super(new CenterMarkerGraphics(), location, width, height);
    }

    public CenterMarker(Point location, int width, int height, int priority) {
        super(new CenterMarkerGraphics(), location, width, height, priority);
    }

    @Override
    protected Object handleClick(int i, int i1) {
        return null;
    }
}
