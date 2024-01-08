package server;
import java.io.IOException;
import java.io.ObjectInputStream;
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
            //Scanner in = new Scanner(clientSocket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream( clientSocket.getInputStream());

            String login = (String) objectInputStream.readObject();
            String password =(String) objectInputStream.readObject();
            Login log = new Login();
            MySQLDatabaseConnector connector = new MySQLDatabaseConnector();
            if (log.check(login, password)) {
                employee = connector.getUserInfo(login, password);
                log.startLogin(employee);
                objectOutputStream.writeObject(employee);
                if (employee instanceof Manager) {
                    List<Order> orders =  employee.getListOfOrder();
                    List<Task> tasks = employee.getListOfTask();
                    List<Employee> employees  =employee.getListOfEmployees();
                    objectOutputStream.writeObject(orders);
                    objectOutputStream.writeObject(tasks);
                    objectOutputStream.writeObject(employees);
                    String answer;
                    do {
                        answer = (String) objectInputStream.readObject();

                    }while (!answer.equals("Close"));
                }
                if (employee instanceof Admin) {
                    List<Employee> employees = ((Admin) employee).getListOfEmployees();
                    objectOutputStream.writeObject(employees);
                    objectOutputStream.writeObject(connector.getRolesList());
                    objectOutputStream.writeObject(connector.getZonesList());
                    String answer;
                    do {
                         answer = (String) objectInputStream.readObject();
                        if (answer.equals("Update")) {
                            Employee updateEmployee = (Employee) objectInputStream.readObject();
                            ((Admin) employee).changeRole(updateEmployee);
                        }
                    }while (!answer.equals("Close"));

//                    List<Order> orders = employee.getListOfOrder();
//                    objectOutputStream.writeObject(orders);
                }
            } else {
                objectOutputStream.writeObject(null);
            }
            clientSocket.close();
            connector.closeConnection();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Tutaj możesz zwrócić dowolną wartość
        return employee;
    }
}