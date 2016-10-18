package crosstheborder.lib;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

/**
 * @author Oscar de Leeuw
 * @author guill
 */
public class LobbyTest {

    private Lobby lobby;

    @Before
    public void setUp() throws Exception {
        lobby = new Lobby(new User("test"), "The Kek Game", "", 8);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addMessage() throws Exception {
        lobby.addMessage(new Message(new User("Henk"), "Dit is een mooi bericht"));
        lobby.addMessage(new Message(new User("Kippetje"), "Dit is idd een geweldig bericht!"));

        for(Message m : lobby.getMessages()){
            System.out.println(m);
        }

        Assert.assertEquals(2, lobby.getMessages().size());
        Assert.assertEquals(LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + " Henk: Dit is een mooi bericht",
                                                                                        lobby.getMessages().get(0).toString());
    }
}