package com.crosstheborder.game.shared.ui.uiobjects;

import com.crosstheborder.game.shared.IGame;
import com.sstengine.component.graphics.GraphicsComponent;
import com.sstengine.ui.UIObject;

import java.awt.*;

/**
 * @author Oscar de Leeuw
 */
public class TimeScoreCounter extends UIObject {
    private IGame game;

    public TimeScoreCounter(GraphicsComponent graphics, Rectangle area, int priority) {
        super(graphics, area, priority);
    }

    public TimeScoreCounter(GraphicsComponent graphics, Rectangle area) {
        super(graphics, area);
    }

    public TimeScoreCounter(GraphicsComponent graphics, Point location, int width, int height) {
        super(graphics, location, width, height);
    }

    public TimeScoreCounter(GraphicsComponent graphics, Point location, int width, int height, int priority) {
        super(graphics, location, width, height, priority);
    }

    @Override
    protected Object handleClick(int x, int y) {
        return null;
    }

    public IGame getGame() {
        return game;
    }
}
