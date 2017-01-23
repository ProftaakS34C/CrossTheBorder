package com.crosstheborder.lobby.server;

import crosstheborder.lib.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guill on 23-1-2017.
 */
public class LobbyTest {

    private LobbyServer lobbyServer;
    private Lobby lobby;
    private User user;
    private Room room;

    @Before
    public void setUp() throws Exception {
        lobby = new Lobby();
        user = new User("Piet");
    }

    /**
     * @throws Exception
     *
     * This method needs to have a User and a Lobby. That user will
     * create a room with the method CreateRoom(). This method
     * will put a room in the list of rooms in Lobby. We can test if it's
     * created successfully by checking the name, maxPlayer and Owner.
     *
     * Also a room has some standard values at the beginning so we can
     * check if those are correctly: IsPrivate, GameStarted, Messages.
     */
    @Test
    public void CreateRoom() throws Exception {

        int roomCapacity = 5;
        String roomName = "TestRoom";

        // Create a new Room in the Lobby
        lobby.createRoom(roomName, roomCapacity, user);

        // check main values
        Assert.assertEquals(roomName, lobby.getRooms().get(0).getName());
        Assert.assertEquals(roomCapacity, lobby.getRooms().get(0).getMaxPlayers());
        Assert.assertEquals(user.getName(), lobby.getRooms().get(0).getOwner().getName());

        // Check other values
        Assert.assertEquals(false, lobby.getRooms().get(0).getIsPrivate());
        Assert.assertEquals(false, lobby.getRooms().get(0).getGameStarted());
        Assert.assertEquals(0, lobby.getRooms().get(0).getMessages().size());
    }

    /**
     * @throws Exception
     *
     * Make a few rooms. Check if those rooms are added to the lobby
     * correctly. Make rooms with different values and check if those values are
     * correctly implemented.
     */
    @Test
    public void LobbyOverzichtTest() throws Exception {

        int roomCapacity = 4;
        int roomCapacity_1 = 7;
        int roomCapacity_2 = 6;

        String roomName = "TestRoom1";
        String roomName_1 = "TestRoom2";
        String roomName_2 = "TestRoom3";

        // Create 3 rooms with random values
        lobby.createRoom(roomName, roomCapacity, user);
        lobby.createRoom(roomName_1, roomCapacity_1, user);
        lobby.createRoom(roomName_2, roomCapacity_2, user);

        Assert.assertEquals(3, lobby.getRooms().size());

        Assert.assertEquals(roomCapacity, lobby.getRooms().get(0).getMaxPlayers());
        Assert.assertEquals(roomName_1, lobby.getRooms().get(1).getName());
        Assert.assertEquals(user.getName(), lobby.getRooms().get(2).getOwner().getName());
    }
}