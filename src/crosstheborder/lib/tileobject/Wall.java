package crosstheborder.lib.tileobject;

import crosstheborder.lib.TileObject;

/**
 * Created by Oscar on 26-Sep-16.
 */
public class Wall extends TileObject{

    int level;

    public Wall(){
        level = 1;
    }

    public void increaseLevel(){
        level++;
    }
}
