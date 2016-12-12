package com.crosstheborder.game.shared.util;

import com.sstengine.game.GameSettings;

/**
 * Created by guill on 5-12-2016.
 */
public class CrossTheBorderGameSettings implements GameSettings {

    public CrossTheBorderGameSettings() {
    }

    @Override
    public int getScoreLimit() {
        return 30;
    }

    @Override
    public void setScoreLimit(int i) {

    }

    @Override
    public int getTimeLimit() {
        return 10 * 5;
    }

    @Override
    public void setTimeLimit(int i) {

    }
}
