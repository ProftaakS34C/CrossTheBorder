package com.crosstheborder.game.shared.ui.uiobjects;

import com.crosstheborder.game.shared.component.graphical.uigraphics.PlaceableTrackerGraphics;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderPlaceableType;
import com.sstengine.player.leader.Leader;
import com.sstengine.player.leader.PlaceableManager;
import com.sstengine.ui.UIObject;

import java.awt.*;

/**
 * @author Oscar de Leeuw
 */
public class PlaceableTracker extends UIObject<PlaceableManager> {
    private Leader leader;
    private CrossTheBorderPlaceableType activeType = CrossTheBorderPlaceableType.WALL;

    public PlaceableTracker(Rectangle area, int priority, Leader leader) {
        super(new PlaceableTrackerGraphics(), area, priority);
        this.leader = leader;
    }

    public PlaceableTracker(Rectangle area, Leader leader) {
        super(new PlaceableTrackerGraphics(), area);
        this.leader = leader;
    }

    public PlaceableTracker(Point location, int width, int height, Leader leader) {
        super(new PlaceableTrackerGraphics(), location, width, height);
        this.leader = leader;
    }

    public PlaceableTracker(Point location, int width, int height, int priority, Leader leader) {
        super(new PlaceableTrackerGraphics(), location, width, height, priority);
        this.leader = leader;
    }

    @Override
    protected PlaceableManager handleClick(int i, int i1) {
        return null;
    }

    public PlaceableManager getManager() {
        return leader.getManager();
    }

    public CrossTheBorderPlaceableType getActiveType() {
        return activeType;
    }

    public void setActiveType(CrossTheBorderPlaceableType type) {
        this.activeType = type;
    }

    public void refresh(Leader leader) {
        this.leader = leader;
    }
}
