package com.crosstheborder.game.shared.ui;

import com.crosstheborder.game.client.input.TrumpAction;
import com.crosstheborder.game.client.input.TrumpInput;
import com.crosstheborder.game.client.input.TrumpInputConverter;
import com.crosstheborder.game.client.input.UIAction;
import com.crosstheborder.game.shared.IGame;
import com.crosstheborder.game.shared.ui.uiobjects.CrossTheBorderCamera;
import com.crosstheborder.game.shared.ui.uiobjects.TimeScoreCounter;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderPlaceableType;
import com.sstengine.component.graphics.Painter;
import com.sstengine.player.PlayerInput;
import com.sstengine.player.leader.Leader;
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
    private TimeScoreCounter scoreCounter;

    private CrossTheBorderPlaceableType activeType;

    public TrumpUI(Painter painter, IGame game, String name) {
        super(painter);

        this.game = game;
        this.name = name;
        activeType = CrossTheBorderPlaceableType.WALL;

        camera = new CrossTheBorderCamera(game, new Point(0, 0), painter.getWidth(), painter.getHeight(), 40);
        scoreCounter = new TimeScoreCounter(new Point(painter.getWidth() / 4, 0), painter.getWidth() / 2, (painter.getHeight() * 8) / 100, game);

        camera.setCenter(new Point(10, 10));

        addUIObject(camera);
        addUIObject(scoreCounter);
    }

    @Override
    public void sendKey(KeyboardKey keyboardKey) {
        KeyCode code = (KeyCode) keyboardKey.getKey();

        if (TrumpInputConverter.isUIAction(code)) {
            handleUIAction(TrumpInputConverter.getUIActionFromCode(code));
        } else {
            try {
                handleTrumpAction(TrumpInputConverter.getTrumpActionFromCode(code));
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }

    @Override
    public void render() {
        /*try {
            System.out.println(game.getElapsedTurns() + "");
            System.out.println("Score of team " + game.getTeams().get(0).getCountry().getTag() + " is " + game.getTeams().get(0).getScore());
            System.out.println("Score of team " + game.getTeams().get(1).getCountry().getTag() + " is " + game.getTeams().get(1).getScore());
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }*/

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

    private void handleTrumpAction(TrumpAction action) throws RemoteException {
        switch (action) {
            case PLACE_OBSTACLE:
                PlayerInput input = new TrumpInput(activeType, game.getMap().getTile(camera.getCenter()));
                game.pushInput(getLeader().getId(), input);
                break;
            case SWITCH_TO_TRAP:
                activeType = CrossTheBorderPlaceableType.TRAP;
                break;
            case SWITCH_TO_WALL:
                activeType = CrossTheBorderPlaceableType.WALL;
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
