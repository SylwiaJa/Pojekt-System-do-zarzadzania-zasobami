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

                    } while (!answer.equals("Close"));
                    log.endLogin(employee);
                }
                if (employee instanceof Manager) {
                    List<Order> orders = employee.getListOfOrder();
                    List<Task> tasks = ((Manager)employee).getListOfTask();
                    List<Employee> employees = ((Manager)employee).getListOfEmployees();
                    List<Equipment> equipments = ((Manager)employee).getListOfEquipment();
                    List<String> taskCategories = ((Manager)employee).getListOfTaskCategory();
                    List<String> equipmentCategories = ((Manager)employee).getListofEquipmentCategory();
                    List<License> licenseList = ((Manager)employee).getLicenseList();
                    List<String> zoneList = ((Manager)employee).getListOfZone();
                    objectOutputStream.writeObject(orders);
                    objectOutputStream.writeObject(tasks);
                    objectOutputStream.writeObject(employees);
                    objectOutputStream.writeObject(equipments);
                    objectOutputStream.writeObject(taskCategories);
                    objectOutputStream.writeObject(equipmentCategories);
                    objectOutputStream.writeObject(licenseList);
                    objectOutputStream.writeObject(zoneList);
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
                            List<List<String>> useEquipment =  employee.getEquipmentTimeOfUseReport(equipmentID);
                            objectOutputStream.writeObject(useEquipment);
                        }
                        if (answer.equals("updateEquipment")) {
                            Equipment equipment = (Equipment) objectInputStream.readObject();
                            employee.changeEquipmentStatus(equipment);
                        }
                        if(answer.equals("getTaskInfo")){
                            Task myTask = (Task) objectInputStream.readObject();
                           List<String> infoTask =   employee.getTaskInfo(myTask);
                           objectOutputStream.writeObject(infoTask);
                        }
                        if(answer.equals("getEmpInfo")){
                            Employee emp = (Employee) objectInputStream.readObject();
                            List<List<String>> empInfo =  employee.getEmpInfo(emp);
                            objectOutputStream.writeObject(empInfo);
                        }
                        if(answer.equals("addTaskCategory")){
                            String category = (String) objectInputStream.readObject();
                            ((Manager) employee).addTaskCategory(category);
                        }
                        if (answer.equals("addLTTC")){
                            List<String> list = (List<String>) objectInputStream.readObject();
                            ((Manager)employee).addLicenseToTaskCategory(list.get(0),list.get(1));
                        }
                    } while (!answer.equals("Close"));
                    log.endLogin(employee);
                    return;
                }
                if(employee instanceof Leader){
                    List<Task> tasks = ((Leader)employee).getListOfTask();
                    List<Employee> employees = ((Leader)employee).getListOfEmployees();
                    List<Equipment> equipments = ((Leader)employee).getListOfEquipment();
                    objectOutputStream.writeObject(tasks);
                    objectOutputStream.writeObject(employees);
                    objectOutputStream.writeObject(equipments);
                    String answer;
                    do{
                        answer = (String) objectInputStream.readObject();
                        if (answer.equals("getUseEquipment")) {
                            int equipmentID = (int) objectInputStream.readObject();
                            List<List<String>> useEquipment = employee.getEquipmentTimeOfUseReport(equipmentID);
                            objectOutputStream.writeObject(useEquipment);
                        }
                        if (answer.equals("updateEquipment")) {
                            Equipment equipment = (Equipment) objectInputStream.readObject();
                            employee.changeEquipmentStatus(equipment);
                        }
                        if(answer.equals("getTaskInfo")){
                            Task myTask = (Task) objectInputStream.readObject();
                            List<String> infoTask =   employee.getTaskInfo(myTask);
                            objectOutputStream.writeObject(infoTask);
                        }
                        if(answer.equals("getEmpInfo")){
                            Employee emp = (Employee) objectInputStream.readObject();
                            List<List<String>> empInfo = employee.getEmpInfo(emp);
                            objectOutputStream.writeObject(empInfo);
                        }
                    }while (!answer.equals("Close"));
                    log.endLogin(employee);
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
                    String answer;
                    do {
                        answer = (String) objectInputStream.readObject();
                        if(answer.equals("endTask")){
                            Task task = (Task) objectInputStream.readObject();
                            employee.endTask(task, employee);
                        }
                        if(answer.equals("takeNewTask")){
                            Task task = (Task) objectInputStream.readObject();
                            employee.acceptTask(task);
                        }
                    }while (answer.equals("CLose"));
                    log.endLogin(employee);
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
