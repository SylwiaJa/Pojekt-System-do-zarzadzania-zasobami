package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Employee employee;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            String login = (String) objectInputStream.readObject();
            String password = (String) objectInputStream.readObject();
            Login log = new Login();
            MySQLDatabaseConnector connector = new MySQLDatabaseConnector();

            if (log.check(login, password)) {
                employee = connector.getUserInfo(login, password);
                log.startLogin(employee);
                objectOutputStream.writeObject(employee);

                if (employee instanceof Manager) {
                    List<Order> orders = employee.getListOfOrder();
                    List<Task> tasks = employee.getListOfTask();
                    List<Employee> employees = employee.getListOfEmployees();
                    List<Equipment> equipments = employee.getListOfEquipment();
                    objectOutputStream.writeObject(orders);
                    objectOutputStream.writeObject(tasks);
                    objectOutputStream.writeObject(employees);
                    objectOutputStream.writeObject(equipments);

                    String answer;
                    do {
                        answer = (String) objectInputStream.readObject();
                        if (answer.equals("orders")) {
                            Order order = (Order) objectInputStream.readObject();
                            String nameProduct = order.getProduct().getName();
                            List<Equipment> equipmentListOfTask = employee.getListOfEquipmentToTask(nameProduct);
                            List<Component> componentListOfTask = employee.getListOfComponentToTask(nameProduct);
                            List<Object> objects = new ArrayList<>();
                            objects.add(equipmentListOfTask);
                            objects.add(componentListOfTask);
                            objectOutputStream.writeObject(objects);
                        }
                        if (answer.equals("addTask")) {
                            Task task = (Task) objectInputStream.readObject();
                            ((Manager) employee).addNewTask(task, employee);
                        }
                        if (answer.equals("getUseEquipment")) {
                            int equipmentID = (int) objectInputStream.readObject();
                            List<List<String>> useEquipment = ((Manager) employee).getEquipmentTimeOfUseReport(equipmentID);
                            objectOutputStream.writeObject(useEquipment);
                        }
                        if (answer.equals("updateEquipment")) {
                            Equipment equipment = (Equipment) objectInputStream.readObject();
                            ((Manager) employee).changeEquipmentStatus(equipment);
                        }
                        if(answer.equals("Close")){
                            log.endLogin(employee);
                        }
                    } while (!answer.equals("Close"));
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
                        if(answer.equals("Close")){
                            log.endLogin(employee);
                        }
                    } while (!answer.equals("Close"));
                }

                if (employee instanceof ProductionEmployee) {
                    Task myTask = ((ProductionEmployee) employee).getMyTask(employee);
                    if (myTask == null) {
                        List<Task> tasksOfEmployee = ((ProductionEmployee) employee).getListOfTask(employee.getId());
                        objectOutputStream.writeObject(tasksOfEmployee);
                    } else {
                        List<Task> tasks = new ArrayList<>();
                        tasks.add(myTask);
                        objectOutputStream.writeObject(tasks);
                    }

                }
            } else {
                objectOutputStream.writeObject(null);
            }

            clientSocket.close();
            connector.closeConnection();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
