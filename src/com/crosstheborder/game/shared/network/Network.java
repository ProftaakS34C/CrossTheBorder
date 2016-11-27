package com.crosstheborder.game.shared.network;

import com.esotericsoftware.kryonet.EndPoint;

/**
 * Contains code that should be shared between a client and a server in terms of networking.
 *
 * @author Oscar de Leeuw
 */
public class Network {
    public static final int DEFAULT_PORT = 24650;

    public static void register(EndPoint endPoint) {
        endPoint.getKryo().register(int[].class);
        endPoint.getKryo().register(Packet.class);
    }
}
