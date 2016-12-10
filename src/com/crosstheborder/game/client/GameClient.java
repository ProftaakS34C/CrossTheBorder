package com.crosstheborder.game.client;

/**
 * @author Oscar de Leeuw
 */
public class GameClient {

    private static GameReceiver gameReceiver;

    public static void main(String[] args) throws Exception {
        gameReceiver = new GameReceiver("HenkArieHansPietje");
    }
}
