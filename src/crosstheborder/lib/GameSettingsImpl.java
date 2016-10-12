package crosstheborder.lib;

import crosstheborder.lib.interfaces.GameSettings;

/**
 * Implementation of the GameSettings interface.
 *
 * @author Oscar de Leeuw
 */
public class GameSettingsImpl implements GameSettings {
    private final int serverTickRate;

    private int scoreLimit = 30;
    private int timeLimit = 600;
    private int respawnTime = 10;
    private int mexicanScoringModifier = 1;
    private int usaScoringModifier = 1;

    private int secondsPerWall = 2;
    private int secondsPerTrap = 4;
    private int initialWallAmount = 10;
    private int initialTrapAmount = 3;

    private int defaultTrapTime = 4;
    private int defaultWallHeight = 2;

    private float climbModifier = 1.5f;


    public GameSettingsImpl(int serverTickRate) {
        this.serverTickRate = serverTickRate;
    }

    @Override
    public int getServerTickRate() {
        return this.serverTickRate;
    }

    @Override
    public int getScoreLimit() {
        return this.scoreLimit;
    }

    @Override
    public void setScoreLimit(int value) {
        this.scoreLimit = value;
    }

    @Override
    public int getTimeLimit() {
        return this.timeLimit;
    }

    @Override
    public void setTimeLimit(int value) {
        this.timeLimit = value;
    }

    @Override
    public int getRespawnTime() {
        return this.respawnTime;
    }

    @Override
    public void setRespawnTime(int value) {
        this.respawnTime = value;
    }

    @Override
    public int getMexicanScoringModifier() {
        return this.mexicanScoringModifier;
    }

    @Override
    public void setMexicanScoringModifier(int value) {
        this.mexicanScoringModifier = value;
    }

    @Override
    public int getUsaScoringModifier() {
        return this.usaScoringModifier;
    }

    @Override
    public void setUsaScoringModifier(int value) {
        this.usaScoringModifier = value;
    }

    @Override
    public int getSecondsPerWall() {
        return this.secondsPerWall;
    }

    @Override
    public void setSecondsPerWall(int value) {
        this.secondsPerWall = value;
    }

    @Override
    public int getSecondsPerTrap() {
        return this.secondsPerTrap;
    }

    @Override
    public void setSecondsPerTrap(int value) {
        this.secondsPerTrap = value;
    }

    @Override
    public int getInitialWallAmount() {
        return this.initialWallAmount;
    }

    @Override
    public void setInitialWallAmount(int value) {
        this.initialWallAmount = value;
    }

    @Override
    public int getInitialTrapAmount() {
        return this.initialTrapAmount;
    }

    @Override
    public void setInitialTrapAmount(int value) {
        this.initialTrapAmount = value;
    }

    @Override
    public int getDefaultTrapTime() {
        return this.defaultTrapTime;
    }

    @Override
    public void setDefaultTrapTime(int value) {
        this.defaultTrapTime = value;
    }

    @Override
    public int getDefaultWallHeight() {
        return this.defaultWallHeight;
    }

    @Override
    public void setDefaultWallHeight(int value) {
        this.defaultWallHeight = value;
    }

    @Override
    public float getClimbModifier() {
        return this.climbModifier;
    }

    @Override
    public void setClimbModifier(float value) {
        this.climbModifier = value;
    }
}
