package com.crosstheborder.game.server;

import com.crosstheborder.game.shared.network.Packet;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * @author Oscar de Leeuw
 */
public class ServerListener extends Listener {
    private GameServer server;

    public ServerListener(GameServer server) {
        this.server = server;
    }

    @Override
    public void connected(Connection connection) {
        System.out.println("Received a connection from " + connection.getRemoteAddressTCP().getHostString());
        server.registerPlayer(connection.getID(), connection);
    }

    @Override
    public void disconnected(Connection connection) {
        //System.out.println("Disconnection from " + connection.getRemoteAddressTCP().getHostString());
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Packet) {
            server.receivedPacket(connection, (Packet) object);
        }
    }
}
