package myproject.com.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import myproject.com.server.Task;
import myproject.com.shared.CommnunicationAction;

public class Client {
    private final Socket socket;
    private final DataInputStream serverReader;
    private final BufferedReader userReader;
    private final DataOutputStream serverWriter;
    private final ObjectInputStream serverObjectReader;

    public Client(String host, int puerto) throws UnknownHostException, IOException {
        // Cliente se conecta
        socket = new Socket(host, puerto);
        serverReader = new DataInputStream(socket.getInputStream());
        userReader = new BufferedReader(new InputStreamReader(System.in));
        serverWriter = new DataOutputStream(socket.getOutputStream());
        serverObjectReader = new ObjectInputStream(socket.getInputStream());
    }

    public void run() throws IOException, ClassNotFoundException {
        // Cliente espera a recibir una "Action" a realizar
        CommnunicationAction action;
        while ((action = listenActionFromServer()) != CommnunicationAction.EXIT) {
            switch (action) {
                case REQUEST_CLIENT_NAME:
                    // Cliente envía su nombre
                    handleRequestClientName();
                    break;
                case REQUEST_TASKS:
                    handleRequestTasks();
                    break;
                case SEND_TASKS:
                    handleSendTasks();
                default:
                    break;
            }
        }

        closeConnections();
        System.out.println("Client -> Connection to Server Finished");
    }

    private CommnunicationAction listenActionFromServer() throws IOException {
        System.out.println("\nClient is waiting for an action.");
        int actionOrdinalKey = serverReader.readInt();

        CommnunicationAction action = CommnunicationAction.values()[actionOrdinalKey];
        System.out.println("Action " + action + " received");
        return action;
    }

    private void handleRequestClientName() throws IOException {
        System.out.println("Please, insert you name");
        String clientName = userReader.readLine();
        serverWriter.writeUTF(clientName);

    }

    private void handleRequestTasks() throws IOException {
        // Cliente solicita nombre al usuario
        int numberOfTaskToDo = requestIntegerInputToUser("Please, insert number of tasks to do");

        // Cliente envía nº de tareas a realizar
        serverWriter.writeInt(numberOfTaskToDo);

        for (int i = 0; i < numberOfTaskToDo; i++) {
            // Cliente recibe nº de tarea a realizar
            int taskNumber = serverReader.readInt();
            // Cliente solicita al usuario la descripción de la tarea y se la envía al
            // servidor
            System.out.println("Please, insert a description for task number " + taskNumber);
            String description = userReader.readLine();
            serverWriter.writeUTF(description.toString());

            // Cliente solicita al usuario el estado de la tarea y se la envía al servidor
            System.out.println("Please, insert the status for task number " + taskNumber);
            String status = userReader.readLine();
            serverWriter.writeUTF(status.toString());
        }
    }

    private void handleSendTasks() throws ClassNotFoundException, IOException {
        // Cliente recibe la información de las tareas.
        Task[] tasks = (Task[]) serverObjectReader.readObject();

        System.out.println("Received " + tasks.length + " tasks:");
        for (Task task : tasks) {
            System.out.println(task.toString());
        }

    }

    private void closeConnections() throws IOException {
        serverReader.close();
        serverWriter.close();
        socket.close();
    }

    private int requestIntegerInputToUser(String message) throws IOException {
        Integer insertedNumber = null;

        System.out.println(message);
        while (true) {
            try {
                insertedNumber = Integer.parseInt(userReader.readLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("The entered value is not a valid number. Please provide a valid numerical input.");
            }
        }
        return insertedNumber.intValue();
    }

}
