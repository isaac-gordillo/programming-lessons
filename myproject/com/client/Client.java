package myproject.com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import myproject.com.server.ServerAction;
import myproject.com.server.Task;

public class Client {
    private Socket socket;
    private BufferedReader serverReader;
    private BufferedReader userReader;
    private PrintWriter serverWriter;
    private ObjectInputStream serverObjectReader;

    public Client(String host, int puerto) throws UnknownHostException, IOException {
        socket = new Socket(host, puerto);

        serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        userReader = new BufferedReader(new InputStreamReader(System.in));
        serverWriter = new PrintWriter(socket.getOutputStream(), true);
        serverObjectReader = new ObjectInputStream(socket.getInputStream());

    }

    public void run() throws IOException, ClassNotFoundException {
        ServerAction action;
        while ((action = listenActionFromServer()) != ServerAction.EXIT) {
            switch (action) {
                case REQUEST_CLIENT_NAME:
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

    private ServerAction listenActionFromServer() throws IOException {
        System.out.println("\nClient is waiting for an action.");
        int actionIndex = Integer.parseInt(serverReader.readLine());

        ServerAction action = ServerAction.values()[actionIndex];
        System.out.println("Action " + action + " received");
        return action;
    }

    private void handleRequestClientName() throws IOException {
        System.out.println("Please, insert you name");
        String clientName = userReader.readLine();
        serverWriter.println(clientName);

    }

    private void handleRequestTasks() throws IOException {
        System.out.println("Please, insert number of tasks to do");
        int numberOfTaskToDo = Integer.parseInt(userReader.readLine());

        serverWriter.println(numberOfTaskToDo);

        for (int i = 0; i < numberOfTaskToDo; i++) {
            String taskNumber = serverReader.readLine();

            System.out.println("Please, insert a description for task number " + taskNumber);
            String description = userReader.readLine();
            serverWriter.println(description);

            System.out.println("Please, insert the status for task number " + taskNumber);
            String status = userReader.readLine();
            serverWriter.println(status);
        }
    }

    private void handleSendTasks() throws ClassNotFoundException, IOException {
        Task[] tasks = (Task[]) serverObjectReader.readObject();

        System.out.println("Received " + tasks.length + " tasks:");
        for (Task task : tasks) {
            System.out.println(task.toString());
        }

    }

    private void closeConnections() throws IOException {
        userReader.close();
        serverReader.close();
        serverWriter.close();
        socket.close();
    }

}
