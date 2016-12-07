package com.crosstheborder.game.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guill on 5-12-2016.
 */
public class TestLobbyServer {

    private static String gameServerBindingName;
    private static String mapName = "mainmap";

    public static void main(String[] args) throws Exception {

        List<String> names = new ArrayList<>();
        names.add("Henk");
        names.add("Arie");
        names.add("Hans");
        names.add("Pietje");

        try{
            // Waiting for a connection
            System.out.println("Waiting...");
            ServerSocket s = new ServerSocket(999);

            // Receive connection
            Socket incoming = s.accept();
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

            } finally {
                incoming.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
