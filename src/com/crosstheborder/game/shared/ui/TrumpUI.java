package com.crosstheborder.game.shared.ui;

import com.crosstheborder.game.client.input.TrumpInputConverter;
import com.crosstheborder.game.client.input.UIAction;
import com.crosstheborder.game.shared.IGame;
import com.crosstheborder.game.shared.ui.uiobjects.CrossTheBorderCamera;
import com.sstengine.component.graphics.Painter;
import com.sstengine.player.leader.Leader;
import com.sstengine.player.playerentity.PlayerEntity;
import com.sstengine.ui.KeyboardKey;
import com.sstengine.ui.UI;
import com.sstengine.util.enumeration.CardinalDirection;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Oscar de Leeuw
 */
public class TrumpUI extends UI {
    private static Logger LOGGER = Logger.getLogger(TrumpUI.class.getName());

    private IGame game;
    private String name;

    private CrossTheBorderCamera camera;

    public TrumpUI(Painter painter, IGame game, String name) {
        super(painter);

        this.game = game;
        this.name = name;
        camera = new CrossTheBorderCamera(game, new Point(0, 0), painter.getWidth(), painter.getHeight(), 40);

        addUIObject(camera);
    }

    @Override
    public void sendKey(KeyboardKey keyboardKey) {
        KeyCode code = (KeyCode) keyboardKey.getKey();

        if (TrumpInputConverter.isUIAction(code)) {
            handleUIAction(TrumpInputConverter.getUIActionFromCode(code));
        }
    }

    @Override
    public void render() {
        try {
            System.out.println(game.getElapsedTurns() + "");
            System.out.println(((PlayerEntity) game.getPlayers().stream().filter(x -> x.getName().equals("Henk")).findFirst().get().getPlayable()).getLocation() + "");
            System.out.println("Score of team " + game.getTeams().get(0).getCountry().getTag() + " is " + game.getTeams().get(0).getScore());
            System.out.println("Score of team " + game.getTeams().get(1).getCountry().getTag() + " is " + game.getTeams().get(1).getScore());
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        camera.refresh();
        super.render();
    }

    private void handleUIAction(UIAction action) {
        Point cameraLoc = camera.getCenter();

        switch (action) {
            case CAMERA_UP:
                camera.setCenter(new Point(cameraLoc.x + CardinalDirection.NORTH.getCartesianRepresentation().x, cameraLoc.y + CardinalDirection.NORTH.getCartesianRepresentation().y));
                break;
            case CAMERA_LEFT:
                camera.setCenter(new Point(cameraLoc.x + CardinalDirection.WEST.getCartesianRepresentation().x, cameraLoc.y + CardinalDirection.WEST.getCartesianRepresentation().y));
                break;
            case CAMERA_RIGHT:
                camera.setCenter(new Point(cameraLoc.x + CardinalDirection.EAST.getCartesianRepresentation().x, cameraLoc.y + CardinalDirection.EAST.getCartesianRepresentation().y));
                break;
            case CAMERA_DOWN:
                camera.setCenter(new Point(cameraLoc.x + CardinalDirection.SOUTH.getCartesianRepresentation().x, cameraLoc.y + CardinalDirection.SOUTH.getCartesianRepresentation().y));
                break;
        }
    }

    private Leader getLeader() {
        try {
            return (Leader) game.getPlayers().stream().filter(x -> x.getName().equals(name)).findFirst().get().getPlayable();
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        System.out.println("FATALE ERREUR");
        System.exit(1);
        return null;
    }
}
