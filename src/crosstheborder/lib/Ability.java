package crosstheborder.lib;

/**
 * This class represents an ability that is used by a Mexican.
 * @author Joram
 * @version 1.0
 */
public abstract class Ability {
    private boolean readyToUse;
    private int cooldownTime;

    /**
     * This is the constructor method of the class "Ability".
     * in the constructor the cooldownTime is set and readyToUse is set to true;
     * @param cooldownTime the time it takes for the ability to be available again.
     */
    public Ability(int cooldownTime){
        this.cooldownTime = cooldownTime;
        this.readyToUse = true;
    }

    /**
     * This method is used to get the readyToUse state of the ability object.
     * @return boolean the value of readyToUse.
     */
    public boolean getReadyToUse(){
        return readyToUse;
    }

    /**
     * Method used to activate the ability. Must be overriden in all children.
     * @return boolean returns true if the ability was activated, if not this method returns false.
     */
    public abstract boolean useAbility();
}
