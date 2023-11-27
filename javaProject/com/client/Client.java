package javaProject.com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.server.ServerCloneException;

import javaProject.com.server.ServerAction;

public class Client {
    private Socket socket;

    public Client(String host, int puerto) throws UnknownHostException, IOException {
        socket = new Socket(host, puerto);
    }

    public void run() throws IOException {
        ServerAction action;
        while ((action = listenActionFromServer()) != ServerAction.EXIT) {
            switch (action) {
                case REQUEST_CLIENT_NAME:
                    handleRequestClientName();
                    break;

                default:
                    break;
            }
        }

        socket.close();
        System.out.println("Client -> Connection to Server Finished");
    }

    private ServerAction listenActionFromServer() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        int actionIndex = reader.read();

        reader.close();

        return ServerAction.values()[actionIndex];
    }

    private void handleRequestClientName() throws IOException {
        BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Please, insert you name");
        String clientName = userReader.readLine();
        writer.println(clientName);

        userReader.close();
        writer.close();
    }
}
