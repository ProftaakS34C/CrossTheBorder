package com.crosstheborder.game.client;

import com.crosstheborder.game.shared.network.Packet;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * @author Oscar de Leeuw
 */
public class ClientListener extends Listener {
    private GameClient client;

    public ClientListener(GameClient client) {
        this.client = client;
    }

    @Override
    public void connected(Connection connection) {

    }

    @Override
    public void disconnected(Connection connection) {

    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Packet) {
            client.receivePacket(connection, (Packet) object);
        }
    }
}
