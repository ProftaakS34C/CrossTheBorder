package crosstheborder.lib.interfaces;

/**
 * Interface for a settings object that allows access to the settings of a game.
 *
 * @author Oscar de Leeuw
 */
public interface GameSettings {
    /**
     * Gets the tick rate of the server.
     *
     * @return The tick rate of the server.
     */
    int getServerTickRate();

    /**
     * Gets the score limit of the game.
     *
     * @return The score limit of the game.
     */
    int getScoreLimit();

    /**
     * Sets the score limit.
     *
     * @param value The value of the new score limit.
     */
    void setScoreLimit(int value);

    /**
     * Gets the time limit of the game.
     *
     * @return The time limit of the game in seconds.
     */
    int getTimeLimit();

    /**
     * Sets the time limit.
     *
     * @param value The value of the new time limit in seconds.
     */
    void setTimeLimit(int value);

    /**
     * Gets the time a player is immobilized after respawning.
     *
     * @return The respawn time.
     */
    int getRespawnTime();

    /**
     * Sets time a player is immobilized after respawning.
     *
     * @param value The value of the new respawn time in seconds.
     */
    void setRespawnTime(int value);

    /**
     * Gets the amount of points that are added to the mexico team upon scoring.
     *
     * @return The amount of points to add to the score.
     */
    int getMexicanScoringModifier();

    /**
     * Sets the amount of points are awarded to the mMxican team after scoring.
     *
     * @param value The amount of points.
     */
    void setMexicanScoringModifier(int value);

    /**
     * Gets the amount of points that are added to the USA team upon soring.
     *
     * @return The amount of points to add to the score.
     */
    int getUsaScoringModifier();

    /**
     * Sets the amount of points are awarded to the USA team after scoring.
     *
     * @param value The amount of points.
     */
    void setUsaScoringModifier(int value);

    /**
     * Gets the amount of seconds it takes for Trump to get a new wall.
     *
     * @return The amount of seconds.
     */
    int getSecondsPerWall();

    /**
     * Sets the amount of seconds it takes for Trump to get a new wall.
     *
     * @param value The amount of seconds.
     */
    void setSecondsPerWall(int value);

    /**
     * Gets the amount of second it takes for Trump to get a new trap.
     *
     * @return The amount of seconds.
     */
    int getSecondsPerTrap();

    /**
     * Sets the amount of seconds it takes for Trump to get a new trap.
     *
     * @param value The amount of seconds.
     */
    void setSecondsPerTrap(int value);

    /**
     * Gets the initial amount of walls Trump starts off with.
     *
     * @return The amount of walls.
     */
    int getInitialWallAmount();

    /**
     * Sets the initial amount of walls Trump starts off with.
     *
     * @param value The initial amount.
     */
    void setInitialWallAmount(int value);

    /**
     * Gets the initial amount of traps Trump starts off with.
     *
     * @return The amount of traps.
     */
    int getInitialTrapAmount();

    /**
     * Sets the initial amount of traps Trump starts off with.
     *
     * @param value The initial amount.
     */
    void setInitialTrapAmount(int value);

    /**
     * Gets the default amount of seconds a trap will immobilize.
     *
     * @return The amount of seconds.
     */
    int getDefaultTrapTime();

    /**
     * Sets the default time a trap will immobilize a player.
     *
     * @param value The default time in seconds.
     */
    void setDefaultTrapTime(int value);

    /**
     * Gets the default height of a wall.
     *
     * @return The height of the wall.
     */
    int getDefaultWallHeight();

    /**
     * Sets the default height of a wall.
     *
     * @param value The default height.
     */
    void setDefaultWallHeight(int value);

    /**
     * Gets the modifier for climbing a wall.
     *
     * @return A float that indicates how many seconds it takes to scale one height of a wall.
     */
    float getClimbModifier();

    /**
     * Sets the climbing modifier for scaling a wall.
     *
     * @param value A float that indicates how many seconds it takes to scale one height of a wall.
     */
    void setClimbModifier(float value);

}
