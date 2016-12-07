package com.crosstheborder.game.server;

import com.crosstheborder.game.shared.factory.MainFactory;
import com.crosstheborder.game.shared.network.Network;
import com.crosstheborder.game.shared.network.Packet;
import com.sstengine.Game;
import com.sstengine.team.Team;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.*;

/**
 * @author Oscar de Leeuw
 */
public class Main {

    private static String bindingName = "";
    private static String mapName;

    private static Timer timer;
    private static MainFactory mainFactory = new MainFactory();
    private static Game game;

    private static List<String> names;

    private static void setupTimer() {

        System.out.println("Timer created");
        timer = new Timer();
        timer.scheduleAtFixedRate(new GamePusher(game, bindingName), 0, 200);
        System.out.println("OK");
    }

    public static void main(String[] args) throws Exception {

        // Receiving list of players + sending bindingName
        try{

            // Creating sockets
            Socket socket = new Socket("localhost", 999);
            try {

                // Creating streams
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                // Getting list of names of streams
                names = (List<String>) in.readObject();

                // Creating bindingName + sending it back
                for(String s : names){
                    bindingName += s;
                }
                out.writeObject(bindingName);

                // Receiving map name
                mapName = (String) in.readObject();
                System.out.println(mapName);

            } finally {
                // Closing socket
                socket.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        // Creating new game
        game = mainFactory.createGame(mapName, names);
        System.out.println("Game Creating");

        setupTimer();
    }
}
