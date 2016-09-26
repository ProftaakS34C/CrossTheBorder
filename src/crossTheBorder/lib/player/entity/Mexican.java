package crossTheBorder.lib.player.entity;

import crossTheBorder.lib.Ability;
import crossTheBorder.lib.enums.MoveDirection;
import crossTheBorder.lib.player.PlayerEntity;

/**
 * This class represents the Mexican player object
 * @author Joram
 * @version 1.0
 */
public class Mexican extends PlayerEntity {
    private boolean isTrapped;
    private Ability ability;

    /**
     * This is the constructor method of the class "Mexican"
     * in the constructor the name of the Mexican is set,
     * the move direction is set,
     * isTrapped is set to false
     * @param name the name of the Mexican
     * @param moveDirection the direction the Mexican is moving
     */
    public Mexican(String name, MoveDirection moveDirection){
        super(name, moveDirection);
        isTrapped = false;
    }

    /**
     * This method is used to get isTrapped
     * @return boolean the value of isTrapped
     */
    public boolean getisTrapped(){
        return isTrapped;
    }

    /**
     * This method is used when a Mexican becomes trapped
     * isTrapped is set to true
     */
    public void becomeTrapped(){
        isTrapped = true;
    }

    /**
     * This method is used when a Mexican uses his ability
     * the method useAbility of class Ability is called
     * @return boolean the value of ability.useAbility
     */
    public boolean useAbility(){
       return ability.useAbility();
    }

}
