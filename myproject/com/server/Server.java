package myproject.com.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import myproject.com.shared.CommnunicationAction;

public class Server {
    public static final int PORT = 9698;
    private ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    public void run() throws IOException, InterruptedException {
        while (true) {
            // Inicia Servidor
            System.out.println("\nServer is waiting for connection");
            Socket clientSocket = serverSocket.accept();
            System.out.println("client connected from " + clientSocket.getInetAddress().getAddress());

            DataOutputStream clientWriter = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream clientReader = new DataInputStream(clientSocket.getInputStream());
            ObjectOutputStream clientObjectWriter = new ObjectOutputStream(clientSocket.getOutputStream());

            // Servidor pregunta nombre del cliente
            System.out.println("Requesting client name");
            clientWriter.writeInt(CommnunicationAction.REQUEST_CLIENT_NAME.ordinal());
            System.out.println("Waiting for client name");
            String clientName = clientReader.readUTF();
            System.out.println("Received client name: " + clientName);

            // Servidor pregunta nº de tareas a realizar
            System.out.println("Requesting number of tasks to client");
            clientWriter.writeInt(CommnunicationAction.REQUEST_TASKS.ordinal());
            System.out.println("Waiting for number of tasks");
            int totalTasks = clientReader.readInt();
            System.out.println("Received " + totalTasks + " tasks to do");

            List<Task> tasks = new ArrayList<Task>();

            for (int taskNumber = 1; taskNumber <= totalTasks; taskNumber++) {
                // Servidor envía al cliente nº de la tarea
                System.out.println("Sending task number " + taskNumber);
                clientWriter.writeInt(taskNumber);

                // Servidor solicita la descripción de la tarea
                System.out.println("Waiting for description of task number " + taskNumber);
                String description = clientReader.readUTF();
                System.out.println("Received description: \"" + description + "\" for task number " + taskNumber);

                // Servidor solicita el estado de la tarea
                System.out.println("Waiting for status of task number " + taskNumber);
                String status = clientReader.readUTF();
                System.out.println("Received status: \"" + status + "\" for task number " + taskNumber);

                // Servidor almacena la tarea en una lista
                tasks.add(new Task(description, status));
            }

            // Servidor envía mensaje al cliente informando de que va a enviar las tareas
            System.out.println("Notifying the client that the task will be sent.");
            clientWriter.writeInt(CommnunicationAction.SEND_TASKS.ordinal());

            // Servidor envía todas las tareas
            System.out.println("Sending tasks to the client");
            clientObjectWriter.writeObject(tasks.toArray(new Task[tasks.size()]));

            clientWriter.writeInt(CommnunicationAction.EXIT.ordinal());
            System.out.println("Connection with finished");
        }

    }

}
