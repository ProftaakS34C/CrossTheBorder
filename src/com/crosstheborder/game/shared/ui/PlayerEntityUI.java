package com.crosstheborder.game.shared.ui;

import com.crosstheborder.game.client.input.PlayerEntityInputConverter;
import com.crosstheborder.game.client.input.UIAction;
import com.crosstheborder.game.shared.IGame;
import com.crosstheborder.game.shared.ui.uiobjects.CrossTheBorderCamera;
import com.sstengine.component.graphics.Painter;
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
public class PlayerEntityUI extends UI {
    private static final Logger LOGGER = Logger.getLogger(PlayerEntityUI.class.getName());

    private IGame game;
    private String name;

    private CrossTheBorderCamera camera;

    private boolean centerCamera = true;

    public PlayerEntityUI(Painter painter, IGame game, String name) throws RemoteException {
        super(painter);

        this.game = game;
        this.name = name;

        camera = new CrossTheBorderCamera(game, new Point(0, 0), painter.getWidth(), painter.getHeight(), 40);
        camera.setCenter(getPlayer().getLocation());
        camera.resize(painter.getWidth(), painter.getHeight());

        addUIObject(camera);
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

        if (centerCamera) {
            camera.setCenter(getPlayer().getLocation());
        }
        camera.refresh();

        super.render();
    }

    @Override
    public void sendKey(KeyboardKey keyboardKey) {
        KeyCode code = (KeyCode) keyboardKey.getKey();

        if (PlayerEntityInputConverter.isUIAction(code)) {
            handleUIAction(PlayerEntityInputConverter.getUIActionFromCode(code));
        } else {
            try {
                game.pushInput(getPlayer().getId(), PlayerEntityInputConverter.getMoveDirectionFromCode(code));
                centerCamera = true;
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }

    public void handleUIAction(UIAction action) {
        Point cameraLoc = camera.getCenter();

        switch (action) {
            case CAMERA_UP:
                camera.setCenter(new Point(cameraLoc.x + CardinalDirection.NORTH.getCartesianRepresentation().x, cameraLoc.y + CardinalDirection.NORTH.getCartesianRepresentation().y));
                centerCamera = false;
                break;
            case CAMERA_LEFT:
                camera.setCenter(new Point(cameraLoc.x + CardinalDirection.WEST.getCartesianRepresentation().x, cameraLoc.y + CardinalDirection.WEST.getCartesianRepresentation().y));
                centerCamera = false;
                break;
            case CAMERA_RIGHT:
                camera.setCenter(new Point(cameraLoc.x + CardinalDirection.EAST.getCartesianRepresentation().x, cameraLoc.y + CardinalDirection.EAST.getCartesianRepresentation().y));
                centerCamera = false;
                break;
            case CAMERA_DOWN:
                camera.setCenter(new Point(cameraLoc.x + CardinalDirection.SOUTH.getCartesianRepresentation().x, cameraLoc.y + CardinalDirection.SOUTH.getCartesianRepresentation().y));
                centerCamera = false;
                break;
        }
    }

    private PlayerEntity getPlayer() {
        try {
            return (PlayerEntity) game.getPlayers().stream().filter(x -> x.getName().equals(name)).findFirst().get().getPlayable();
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        System.out.println("FATALE ERREUR");
        System.exit(1);
        return null;
    }
}
