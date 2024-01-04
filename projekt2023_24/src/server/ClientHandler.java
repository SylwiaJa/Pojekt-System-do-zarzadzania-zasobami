package server;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class ClientHandler implements Callable<Employee> {
    private Socket clientSocket;
    private Employee employee;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public Employee call() {
        try {
            Scanner in = new Scanner(clientSocket.getInputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            String login = in.nextLine();
            String password = in.nextLine();
            Login log = new Login();
            MySQLDatabaseConnector connector = new MySQLDatabaseConnector();
            if (log.check(login, password)) {
                employee = connector.getUserInfo(login, password);
                log.startLogin(employee);
                objectOutputStream.writeObject(employee);
                if (employee instanceof Manager) {
                    List<Order> orders = ((Manager) employee).getListOfOrder();
                    objectOutputStream.writeObject(orders);
                }
                if (employee instanceof Admin) {
                    List<Employee> employees = ((Admin) employee).getListOfEmployees();
                    objectOutputStream.writeObject(employees);
                    String answer = in.nextLine();
                    if (answer.equals("getRolesAndZones")) {
                        objectOutputStream.writeObject(connector.getRolesList());
                        objectOutputStream.writeObject(connector.getZonesList());
                    }
//                    List<Order> orders = employee.getListOfOrder();
//                    objectOutputStream.writeObject(orders);
                }
            } else {
                objectOutputStream.writeObject(null);
            }
            //   log.endLogin(employee);
            clientSocket.close();
            connector.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Tutaj możesz zwrócić dowolną wartość int
        return employee;
    }
}