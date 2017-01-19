package com.crosstheborder.game.shared.ui;

import com.crosstheborder.game.client.input.TrumpAction;
import com.crosstheborder.game.client.input.TrumpInput;
import com.crosstheborder.game.client.input.TrumpInputConverter;
import com.crosstheborder.game.client.input.UIAction;
import com.crosstheborder.game.shared.IGame;
import com.crosstheborder.game.shared.ui.uiobjects.CenterMarker;
import com.crosstheborder.game.shared.ui.uiobjects.CrossTheBorderCamera;
import com.crosstheborder.game.shared.ui.uiobjects.PlaceableTracker;
import com.crosstheborder.game.shared.ui.uiobjects.TimeScoreCounter;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderPlaceableType;
import com.sstengine.component.graphics.Painter;
import com.sstengine.map.Map;
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
public class TrumpUI extends UIExtension {
    private static Logger LOGGER = Logger.getLogger(TrumpUI.class.getName());

    private Map map;

    private CrossTheBorderCamera camera;
    private TimeScoreCounter scoreCounter;
    private PlaceableTracker placeableTracker;

    public TrumpUI(Painter painter, IGame game, String name) throws RemoteException {
        super(painter, game, name);

        this.map = game.getMap();

        camera = new CrossTheBorderCamera(map, new Point(0, 0), painter.getWidth(), painter.getHeight(), 40);
        camera.setCenter(new Point(10, 10));

        Rectangle timeCounterRectangle = new Rectangle((painter.getWidth() * 40) / 100, 0, (painter.getWidth() * 20 / 100), (painter.getHeight() * 6) / 100);
        scoreCounter = new TimeScoreCounter(timeCounterRectangle, game);

        Rectangle placeableTrackerRectangle = new Rectangle(painter.getWidth() * 2 / 100, painter.getHeight() * 40 / 100, painter.getWidth() * 5 / 100, painter.getHeight() * 10 / 100);
        placeableTracker = new PlaceableTracker(placeableTrackerRectangle, getLeader());

        CenterMarker centerMarker = new CenterMarker(new Point(painter.getWidth() / 2 - 20, painter.getHeight() / 2), 40, 40);

        addUIObject(camera);
        addUIObject(scoreCounter);
        addUIObject(centerMarker);
        addUIObject(placeableTracker);
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
        try {
            map = getGame().getMap();
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        camera.refresh(map);
        placeableTracker.refresh(getLeader());

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
                PlayerInput input = new TrumpInput(placeableTracker.getActiveType(), getGame().getMap().getTile(camera.getCenter()));
                getGame().pushInput(getLeader().getId(), input);
                break;
            case SWITCH_TO_TRAP:
                placeableTracker.setActiveType(CrossTheBorderPlaceableType.TRAP);
                break;
            case SWITCH_TO_WALL:
                placeableTracker.setActiveType(CrossTheBorderPlaceableType.WALL);
                break;
        }
    }

    private Leader getLeader() {
        try {
            return (Leader) getGame().getPlayers().stream().filter(x -> x.getName().equals(getName())).findFirst().get().getPlayable();
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        System.out.println("FATALE ERREUR");
        System.exit(1);
        return null;
    }
}
