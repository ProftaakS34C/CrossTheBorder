package com.crosstheborder.game.client;

import com.crosstheborder.game.shared.network.Packet;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Oscar de Leeuw
 */
public class GameClient {

    private static GameReceiver gameReceiver;

    public static void main(String[] args) throws Exception {
        gameReceiver = new GameReceiver();
    }
}
