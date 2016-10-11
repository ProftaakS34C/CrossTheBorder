package crosstheborder.lib;

import crosstheborder.lib.interfaces.GameSettings;

/**
 * Implementation of the GameSettings interface.
 *
 * @author Oscar de Leeuw
 */
public class GameSettingsImpl implements GameSettings {
    private final int serverTickRate;

    private int scoreLimit;
    private int timeLimit;
    private int respawnTime;
    private int mexicanScoringModifier;
    private int usaScoringModifier;

    private int secondsPerWall;
    private int secondsPerTrap;
    private int initialWallAmount;
    private int initialTrapAmount;

    private int defaultTrapTime;
    private int defaultWallHeight;

    private float climbModifier;


    public GameSettingsImpl(int serverTickRate) {
        this.serverTickRate = serverTickRate;
    }

    @Override
    public int getServerTickRate() {
        return this.getServerTickRate();
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
