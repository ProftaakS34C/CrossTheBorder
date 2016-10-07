package crosstheborder.lib;

/**
 * Created by Oscar on 26-Sep-16.
 * player is the super class for Trump and PlayerEntity
 */
public abstract class Player {

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
