package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Employee employee;

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
            Login log = new Login();
            MySQLDatabaseConnector connector = new MySQLDatabaseConnector();
            if (log.check(login,password)) {
                employee = connector.getUserInfo(login, password);
                log.startLogin(employee);
                objectOutputStream.writeObject(employee);

            }else {
                objectOutputStream.writeObject(null);
            }
         //   log.endLogin(employee);
            clientSocket.close();
            connector.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}