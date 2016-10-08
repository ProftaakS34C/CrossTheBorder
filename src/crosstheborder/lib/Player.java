package crosstheborder.lib;

/**
 * Player is the super class for Trump and PlayerEntity.
 *
 * @author Oscar de Leeuw
 */
public abstract class Player {
    protected static final int SERVER_TICK_RATE = ServerSettings.getInstance().getServerTickRate();

    private String name;

    /**
     * Abstract constructor that sets the name of the player.
     *
     * @param name The name of the player.
     */
    public Player(String name) {

        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
