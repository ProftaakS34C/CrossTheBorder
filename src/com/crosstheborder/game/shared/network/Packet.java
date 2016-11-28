package com.crosstheborder.game.shared.network;

/**
 * @author Oscar de Leeuw
 */
public class Packet {
    public int packetType;
    public int[] arguments;

    public Packet() {

    }

    public Packet(int packetType, int... args) {
        this.packetType = packetType;
        this.arguments = args;
    }

    @Override
    public String toString() {
        String ret = "PacketType: " + packetType + "\n";
        for (int i = 0; i < arguments.length; i++) {
            ret += "Argument " + i + ":" + arguments[i] + "\n";
        }
        return ret;
    }
}
