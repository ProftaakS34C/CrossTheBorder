package com.crosstheborder.game.server;

import com.crosstheborder.game.shared.network.RMIConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guill on 5-12-2016.
 * @author Oscar de Leeuw
 */
public class TestLobbyServer {

    private static String gameServerBindingName;
    private static String mapName = "mainmap";

    public static void main(String[] args) {

        List<String> names = new ArrayList<>();
        names.add("Henk");
        names.add("Arie");
        names.add("Hans");
        names.add("Pietje");

        try{
            new Thread(() -> GameServer.main(null)).start();

            // Connect to the GameServer
            Socket incoming = new Socket(RMIConstants.GAME_SERVER_LOCATION, RMIConstants.SOCKET_PORT);
            System.out.println("Connected");

            try{

                // Creating output + input stream
                ObjectInputStream in = new ObjectInputStream(incoming.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(incoming.getOutputStream());

                // Writing names
                out.writeObject(names);

                // Receiving bindingname
                gameServerBindingName = (String)in.readObject();

                // Writing mapName
                out.writeObject(mapName);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                incoming.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
