package com.crosstheborder.game.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guill on 5-12-2016.
 * @author Oscar de Leeuw
 */
public class TestLobbyServer {

    private static String gameServerBindingName = "Henk,Arie,Hans,Pietje";
    private static String mapName = "mainmap";

    public static void main(String[] args) {

        List<String> names = new ArrayList<>();
        names.add("Henk");
        names.add("Arie");
        names.add("Hans");
        names.add("Pietje");

        new Thread(() -> GameServer.main(new String[]{"-m", gameServerBindingName, mapName})).start();

        /*try{


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
        }*/
    }
}
