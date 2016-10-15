package crosstheborder.lib.enumeration;

import crosstheborder.lib.interfaces.GameSettings;
import crosstheborder.lib.tileobject.Placeable;
import crosstheborder.lib.tileobject.placeable.Trap;
import crosstheborder.lib.tileobject.placeable.Wall;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Stores all the different types of Placeables there are.
 * Also functions as a factory with the getPlaceable method.
 *
 * @author Oscar de Leeuw
 */
public enum PlaceableType {
    WALL(Wall.class),
    TRAP(Trap.class);

    private Class<? extends Placeable> placeableClass;

    PlaceableType(Class<? extends Placeable> placeableClass) {
        this.placeableClass = placeableClass;
    }

    /**
     * Returns a new instance of the class that is associated with the PlaceableType enum.
     *
     * @param settings The settings of the game. These are given to the placeable.
     * @return A placeable object of the class of the enum.
     * @throws Exception An exception indicating that the class cannot be created.
     */
    public Placeable getPlaceable(GameSettings settings) throws Exception {
        try {
            //Will raise runtime errors when a placeable constructor is changed.
            Constructor<?> ctor = placeableClass.getConstructor(GameSettings.class);
            return (Placeable) ctor.newInstance(settings);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new Exception("Invalid PlaceableType");
            //TODO make a custom exception. InvalidPlaceableType,
        }
    }
}
