package myproject.com.server;

import java.io.IOException;

/**
 * Main
 */
public class MainServer {

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.run();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}