package myproject.com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int PORT = 9698;
    private ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    public void run() throws IOException, InterruptedException {
        while (true) {
            System.out.println("\nServer is waiting for connection");
            Socket clientSocket = serverSocket.accept();
            System.out.println("client connected from " + clientSocket.getInetAddress().getAddress());

            PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ObjectOutputStream clientObjectWriter = new ObjectOutputStream(clientSocket.getOutputStream());

            // Request name to the client
            System.out.println("Requesting client name");
            clientWriter.println(ServerAction.REQUEST_CLIENT_NAME.ordinal());
            System.out.println("Waiting for client name");
            String clientName = clientReader.readLine();
            System.out.println("Received client name: " + clientName);

            // Request tasks to client
            System.out.println("Requesting number of tasks to client");
            clientWriter.println(ServerAction.REQUEST_TASKS.ordinal());
            System.out.println("Waiting for number of tasks");
            int totalTasks = Integer.parseInt(clientReader.readLine());
            System.out.println("Received " + totalTasks + " tasks to do");

            List<Task> tasks = new ArrayList<Task>();

            for (int taskNumber = 1; taskNumber <= totalTasks; taskNumber++) {
                System.out.println("Sending task number " + taskNumber);
                clientWriter.println(taskNumber);

                System.out.println("Waiting for description of task number " + taskNumber);
                String description = clientReader.readLine();
                System.out.println("Received description: \"" + description + "\" for task number " + taskNumber);

                System.out.println("Waiting for status of task number " + taskNumber);
                String status = clientReader.readLine();
                System.out.println("Received status: \"" + status + "\" for task number " + taskNumber);

                tasks.add(new Task(description, status));
            }

            System.out.println("Notify the client of task submission");
            clientWriter.println(ServerAction.SEND_TASKS.ordinal());

            System.out.println("Sending tasks to the client");
            clientObjectWriter.writeObject(tasks.toArray(new Task[tasks.size()]));

            clientWriter.println(ServerAction.EXIT.ordinal());
            System.out.println("Connection with finished");
        }

    }

}
