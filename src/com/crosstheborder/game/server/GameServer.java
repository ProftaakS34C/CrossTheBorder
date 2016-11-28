package com.crosstheborder.game.server;

import com.crosstheborder.game.shared.network.Network;
import com.crosstheborder.game.shared.network.Packet;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Oscar de Leeuw
 */
public class GameServer {
    private Server server;
    private ServerListener listener;
    //Map of PlayerId and Connection.
    private Map<Integer, Connection> connections;

    private int port;

    public GameServer(int port) throws IOException {
        this.port = port;
        this.server = new Server();
        this.listener = new ServerListener(this);
        this.connections = new HashMap<>();

        //Register all data types.
        Network.register(server);

        //Bind the server.
        server.bind(port, port);
        //Start the server.
        server.start();
        //Add the ServerListener.
        server.addListener(listener);
    }

    public synchronized void registerPlayer(int playerId, Connection connection) {
        System.out.println("Registering connection from " + connection.getRemoteAddressTCP().getHostString() + " under the ID " + playerId);
        connections.put(playerId, connection);
    }

    public synchronized void sendPacket(int playerId, Packet packet) {
        Connection conn = connections.get(playerId);
        if (conn != null) {
            System.out.println("Sending a packet to " + conn.getRemoteAddressTCP().getHostString());
            conn.sendTCP(packet);
        }
    }

    public synchronized void receivedPacket(Connection connection, Packet packet) {
        //Send the packet to a PacketManager.
        System.out.println("Got a packet from " + connection.getRemoteAddressTCP().getHostString());
        System.out.println("Packet contents:");
        System.out.println(packet);
    }
}
