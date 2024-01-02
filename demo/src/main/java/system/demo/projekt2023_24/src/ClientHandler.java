import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(clientSocket.getInputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            String login = in.nextLine();
            String password = in.nextLine();
            System.out.println("Login: " + login);
            System.out.println("Hasło: " + password);
            MySQLDatabaseConnector  connector = new MySQLDatabaseConnector();
           Employee employee = connector.getUserInfo(login,password);
            objectOutputStream.writeObject(employee);

            clientSocket.close();
            connector.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
