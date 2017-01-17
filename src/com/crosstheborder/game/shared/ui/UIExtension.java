package com.crosstheborder.game.shared.ui;

import com.crosstheborder.game.shared.IGame;
import com.sstengine.component.graphics.Painter;
import com.sstengine.player.playerentity.PlayerEntity;
import com.sstengine.player.Player;
import com.sstengine.ui.KeyboardKey;
import com.sstengine.ui.UI;
import com.sstengine.ui.UIObject;

import java.rmi.RemoteException;

/**
 * Created by yannic on 16/01/2017.
 */
public abstract class UIExtension extends UI{
    private IGame game;
    private String name;
    private Painter painter;
    private EndScreen endScreen;

    public UIExtension(Painter painter, IGame game, String name) {
        super(painter);
        this.name = name;
        this.game = game;
        this.painter = painter;
        endScreen = new EndScreen(game, this);

        endScreen.setHidden(true);

        addUIObject(endScreen);
    }

    @Override
    public abstract void sendKey(KeyboardKey keyboardKey);

    public IGame getGame() {
        return game;
    }

    public String getName() {
        return name;
    }


    public Player getPlayer(){
        try {
            return game.getPlayers().stream().filter(x -> x.getName().equals(name)).findFirst().get();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addUIObject(UIObject uiObject) {
        super.addUIObject(uiObject);
    }

    public int getWidth(){
        return painter.getWidth();
    }

    public int getHeight(){
        return painter.getHeight();
    }

    public void showEndScreen(){
        uiObjects.removeIf(UIObject -> UIObject != endScreen);
        endScreen.loadValues();
        endScreen.setHidden(false);
        render();
    }
}
