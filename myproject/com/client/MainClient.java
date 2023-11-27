package myproject.com.client;

import java.io.IOException;
import java.net.UnknownHostException;

import myproject.com.server.Server;

/**
 * Main
 */
public class MainClient {

    public static void main(String[] args) throws UnknownHostException, IOException {
        Client client = new Client("localhost", Server.PORT);

        client.run();
    }
}