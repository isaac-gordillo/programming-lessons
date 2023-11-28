package myproject.com.client;

import java.io.IOException;
import myproject.com.server.Server;

/**
 * Main
 */
public class MainClient {

    public static void main(String[] args)  {
        try {
            Client client = new Client("localhost", Server.PORT);

            client.run();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}