package com.crosstheborder.game.client;

import com.crosstheborder.game.shared.network.Network;
import com.crosstheborder.game.shared.network.Packet;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

import java.io.IOException;

/**
 * @author Oscar de Leeuw
 */
public class GameClient {
    private String ipAdress;

    private Client client;
    private ClientListener listener;

    public GameClient(String ipAdress) throws IOException {
        this.client = new Client();
        this.ipAdress = ipAdress;
        this.listener = new ClientListener(this);

        Network.register(client);
        client.start();

        client.connect(5000, ipAdress, Network.DEFAULT_PORT, Network.DEFAULT_PORT);

        client.addListener(listener);
    }

    public synchronized void sendPacket(Packet packet) {
        System.out.println("Sending a packet to the server.");
        client.sendTCP(packet);
    }

    public synchronized void receivePacket(Connection conn, Packet packet) {
        System.out.println("Got a packet from " + conn.getRemoteAddressTCP().getHostString());
        System.out.println(packet);
    }
}
