package com.crosstheborder.game.shared.ui.uiobjects;

import com.crosstheborder.game.shared.IGame;
import com.crosstheborder.game.shared.component.graphical.uigraphics.TimeScoreCounterGraphics;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderCountryTag;
import com.sstengine.ui.UIObject;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Oscar de Leeuw
 */
public class TimeScoreCounter extends UIObject {
    private static Logger LOGGER = Logger.getLogger(TimeScoreCounter.class.getName());

    private IGame game;

    public TimeScoreCounter(Rectangle area, int priority, IGame game) {
        super(new TimeScoreCounterGraphics(), area, priority);
        constructor(game);
    }

    public TimeScoreCounter(Rectangle area, IGame game) {
        super(new TimeScoreCounterGraphics(), area);
        constructor(game);
    }

    public TimeScoreCounter(Point location, int width, int height, IGame game) {
        super(new TimeScoreCounterGraphics(), location, width, height);
        constructor(game);
    }

    public TimeScoreCounter(Point location, int width, int height, int priority, IGame game) {
        super(new TimeScoreCounterGraphics(), location, width, height, priority);
        constructor(game);
    }

    private void constructor(IGame game) {
        this.game = game;
    }

    @Override
    protected Object handleClick(int x, int y) {
        return null;
    }

    public int scoreUSA() {
        try {
            return game.getTeams().stream().filter(x -> x.getCountry().getTag() == CrossTheBorderCountryTag.USA).findFirst().get().getScore();
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return 0;
    }

    public int scoreMEX() {
        try {
            return game.getTeams().stream().filter(x -> x.getCountry().getTag() == CrossTheBorderCountryTag.MEX).findFirst().get().getScore();
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return 0;
    }

    public String remainingTime() {
        String ret = "";

        try {
            int remainingTicks = game.getRemainingTurns();
            ret = String.format("%1$d:%2$02d", (remainingTicks / 5) / 60, (remainingTicks / 5) % 60);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return ret;
    }
}
