package crosstheborder.lib;

import com.crosstheborder.lobby.server.Room;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

/**
 * @author Oscar de Leeuw
 * @author guill
 */
public class RoomTest {

    private Room room;

    @Before
    public void setUp() throws Exception {
        room = new Room(new User("test"), "The Kek Game", "", 8);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addMessage() throws Exception {
        room.addMessage(new Message(new User("Henk"), "Dit is een mooi bericht"));
        room.addMessage(new Message(new User("Kippetje"), "Dit is idd een geweldig bericht!"));

        for(Message m : room.getMessages()){
            System.out.println(m);
        }

        Assert.assertEquals(2, room.getMessages().size());
        Assert.assertEquals(LocalTime.now().getHour() + ":0" + LocalTime.now().getMinute() + " Henk: Dit is een mooi bericht",
                                                                                        room.getMessages().get(0).toString());
    }
}