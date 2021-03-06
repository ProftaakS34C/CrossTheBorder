package com.crosstheborder.game.server;

import com.crosstheborder.game.shared.CrossTheBorderGame;
import com.crosstheborder.game.shared.factory.MainFactory;
import com.crosstheborder.game.shared.network.RMIConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author Oscar de Leeuw
 */
public class GameServer {
    private static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());

    private static String bindingName = "";
    private static String mapName;

    private static Timer timer;
    private static MainFactory mainFactory = new MainFactory();
    private static CrossTheBorderGame game;
    private static GamePusher pusher;

    private static List<String> names;

    private static void fixLoggerForDebugging() {
        LOGGER.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(handler);
        LOGGER.fine("THIS IS ONLY FOR DEBUGGING. SHOULD NOT BE PRESENT IN PRODUCTION CODE.");
    }

    public static void main(String[] args) {
        //args[0] = -m
        //args[1] = names with comma
        //args[2] = mapname
        timer = new Timer();
        fixLoggerForDebugging();

        if (args[0].equals("-m")) {
            names = new ArrayList<>(Arrays.asList(args[1].split(",")));
            bindingName = String.join("", args[1].split(","));
            mapName = args[2];
        } else {
            awaitLobbyConnection();
        }

        // Creating new game
        LOGGER.log(Level.FINE, "Creating game...");
        game = mainFactory.createGame(mapName, names);
        pusher = new GamePusher(game, bindingName);
        LOGGER.log(Level.FINE, "Created game and pusher.");

        setupTimer();
        LOGGER.log(Level.FINE, "Started game.");
    }

    private static void awaitLobbyConnection() {
        // Receiving list of players + sending bindingName
        try{
            ServerSocket serverSocket = new ServerSocket(RMIConstants.SOCKET_PORT);
            LOGGER.log(Level.FINE, "Awaiting connection from lobby server.");
            serverSocket.setSoTimeout(10000);

            try (Socket socket = serverSocket.accept()) {
                LOGGER.log(Level.FINE, "Got a connection from: " + socket.getInetAddress().getHostAddress());

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
            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
                System.exit(2);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            System.exit(1);
        }

        //System.exit(0);
    }

    private static void setupTimer() {
        timer.scheduleAtFixedRate(pusher, 0, 200);
    }
}
