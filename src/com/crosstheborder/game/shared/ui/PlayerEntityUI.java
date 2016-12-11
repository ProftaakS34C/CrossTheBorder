package com.crosstheborder.game.shared.ui;

import com.crosstheborder.game.client.input.PlayerEntityInputConverter;
import com.crosstheborder.game.client.input.UIAction;
import com.crosstheborder.game.shared.IGame;
import com.sstengine.component.graphics.Painter;
import com.sstengine.player.playerentity.PlayerEntity;
import com.sstengine.ui.KeyboardKey;
import com.sstengine.ui.UI;
import com.sstengine.ui.uiobjects.Camera;
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
    private PlayerEntity player;

    private Camera camera;

    public PlayerEntityUI(Painter painter, IGame game, String name) throws RemoteException {
        super(painter);

        this.game = game;
        player = (PlayerEntity) game.getPlayers().stream().filter(x -> x.getName().equals(name)).findFirst().get().getPlayable();

        camera = new Camera(game.getMap(), new Point(0, 0), painter.getWidth(), painter.getHeight(), 40);
        camera.setCenter(player.getLocation());
        camera.resize(painter.getWidth(), painter.getHeight());

        addUIObject(camera);
    }

    @Override
    public void render() {
        try {
            System.out.println(game.getElapsedTurns() + "");
            System.out.println(((PlayerEntity) game.getPlayers().stream().filter(x -> x.getName().equals("Henk")).findFirst().get().getPlayable()).getLocation() + "");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.render();
    }

    @Override
    public void sendKey(KeyboardKey keyboardKey) {
        KeyCode code = (KeyCode) keyboardKey.getKey();

        if (PlayerEntityInputConverter.isUIAction(code)) {
            handleUIAction(PlayerEntityInputConverter.getUIActionFromCode(code));
        } else {
            try {
                game.pushInput(player.getId(), PlayerEntityInputConverter.getMoveDirectionFromCode(code));
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
}