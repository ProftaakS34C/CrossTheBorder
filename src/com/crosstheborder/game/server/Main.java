package com.crosstheborder.game.server;

import com.crosstheborder.game.shared.network.Network;
import com.crosstheborder.game.shared.network.Packet;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Oscar de Leeuw
 */
public class Main {
    public static GameServer gameServer;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        gameServer = new GameServer(Network.DEFAULT_PORT);

        while (true) {
            System.out.println("Type 'Q' to exit, any other character to send a packet.");
            if (scanner.next().equals("Q")) {
                break;
            }

            System.out.print("Give the client ID: ");
            int clientId = scanner.nextInt();

            System.out.print("Give the PacketType: ");
            int packetType = scanner.nextInt();
            ArrayList<Integer> arguments = new ArrayList<>();

            System.out.println("Give the arguments, type '0' to end.");
            while (true) {
                int next = scanner.nextInt();
                if (next == 0) {
                    break;
                }
                arguments.add(next);

                System.out.println("Give another argument or type '0' to end.");
            }

            Packet packet = new Packet(packetType, arguments.stream().mapToInt(i -> i).toArray());
            gameServer.sendPacket(clientId, packet);
        }

        System.exit(0);
    }
}
