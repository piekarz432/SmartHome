package Clas;

import java.io.*;
import java.net.ServerSocket;

/**
 * Klasa main serwera.
 */
public class Main {

    /**
     * Metoda main serwera.
     * @param args - argumenty wywołania
     */
    public static void main(String[] args) {
        Datasource.openConnection();
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            while (true) {
                new Datasource(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
