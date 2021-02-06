package Clas;

import java.io.IOException;
import java.net.Socket;

/**
 * Klasa towrzaca nowe gniazdo(socekt).
 */
public class Server {

    /**
     * Gniazdo(socket).
     */
    private static Socket socket;

    /**
     * Konstruktor domyslny.
     */
    public Server() {
    }

    /**
     * Inicjalizacja statyczna.
     */
    static {
        try {
            socket = new Socket("localhost", 5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return Zwraca gniazdo(socket)
     */
    public static Socket getSocket() {
        return socket;
    }
}
