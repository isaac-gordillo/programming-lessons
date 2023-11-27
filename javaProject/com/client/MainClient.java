package javaProject.com.client;

import java.io.IOException;
import java.net.UnknownHostException;

import javaProject.com.server.Server;

/**
 * Main
 */
public class MainClient {

    public static void main(String[] args) throws UnknownHostException, IOException {
        Client client = new Client("localhost", Server.PORT);

        client.run();
    }
}