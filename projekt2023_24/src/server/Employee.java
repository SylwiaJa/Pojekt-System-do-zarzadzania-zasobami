package server;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Employee implements Serializable {
    private int id;
    private String name;
    private String lastName;
    private String role;
    private String zone;
    private String login;
    private String password;
    private Task task;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/system";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;
    private String startTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Employee(int id, String name, String lastName, String role, String zone, String login, String password) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.zone = zone;
        this.login = login;
        this.password = password;
    }
    public Employee(int id, String name, String lastName, String role, String zone) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.zone = zone;
    }
    public Employee(int id, String name, String lastName, String role, String zone, String login, String password, Task task) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.zone = zone;
        this.login = login;
        this.password = password;
        this.task = task;
    }
    public void getTask(Task task){

    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getZone() {
        return zone;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void acceptTask(Task task){

    }
    public void endTask(Task task){

    }
    public void addTaskResult(Task task, Result result){

    }
    public List<Order> getListOfOrder(){
        List<Order> orders = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT \n" +
                "    oq.orderQuantityID,\n" +
                "    p.productID,\n" +
                "    p.name AS productName,\n" +
                "    o.Status AS orderStatus,\n" +
                "    oq.quantityOrdered,\n" +
                "    oq.QuantityInProduction,\n" +
                "    oq.OuantityFinished\n" +
                "FROM \n" +
                "    orderQuantity oq\n" +
                "JOIN \n" +
                "    product p ON oq.productID = p.productID\n" +
                "JOIN \n" +
                "    orders o ON oq.orderID = o.OrderID;";
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()){
                int id = result.getInt("orderQuantityID");
                int productID = result.getInt("productID");
                String productName = result.getString("productName");
                String orderStatus = result.getString("orderStatus");
                int quantityOrdered = result.getInt("quantityOrdered");
                int quantityInProduction = result.getInt("QuantityInProduction");
                int quantityFinished = result.getInt("OuantityFinished");
                orders.add(new Order(id, orderStatus,new Product(productID,productName,quantityOrdered,quantityInProduction,quantityFinished)));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return  orders;
    }
    public List<Employee> getListOfEmployees(){
        List<Employee> employees = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT e.employeeID, e.name, e.lastName, r.roleName AS role, z.name AS zone\n" +
                "FROM Employee e\n" +
                "JOIN role r ON e.roleID = r.roleID\n" +
                "JOIN zone z ON e.zoneID = z.zoneID;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("employeeID");
                String name = result.getString("name");
                String lastName = result.getString("lastName");
                String role = result.getString("role");
                String zone = result.getString("zone");
                employees.add(new Employee(id, name,lastName,role,zone));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  employees;
    }
    public List<Task> getListOfTask(){
        List<Task> tasks = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT \n" +
                "    t.taskID AS taskID,\n" +
                "    t.name AS name,\n" +
                "    t.priority,\n" +
                "    t.description,\n" +
                "    t.norm,\n" +
                "    z.name AS zone_name,\n" +
                "    t.quantity\n" +
                "FROM \n" +
                "    Task t\n" +
                "JOIN \n" +
                "    Zone z ON t.zoneID = z.zoneID;\n";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("taskID");
                String name = result.getString("name");
                String priority = result.getString("priority");
                String description = result.getString("description");
                int norm = result.getInt("norm");
                String zoneName = result.getString("zone_name");
                int quantity = result.getInt("quantity");
                tasks.add(new Task(id, name,priority,description,norm,zoneName,quantity));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  tasks;
    }
    public List<Equipment> getListOfEquipment(){
        List<Equipment> equipments = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        String query = "SELECT \n" +
                "    e.equipmentID AS equipmentID,\n" +
                "    e.name AS equipmentName,\n" +
                "    e.status,\n" +
                "    z.name AS zoneName\n" +
                "FROM \n" +
                "    equipment e\n" +
                "JOIN \n" +
                "    zone z ON e.zoneID = z.zoneID;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("equipmentID");
                String name = result.getString("equipmentName");
                String status = result.getString("status");
                String zoneName = result.getString("zoneName");
                equipments.add(new Equipment(id, name,status,zoneName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return equipments;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public List<Equipment> getListOfEquipmentToTask(String nameProduct) {
        List<Equipment> equipmentList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query="SELECT equipment.equipmentID,equipment.name, equipmentCategory.name AS equipment_category, equipment.status, zone.name AS equipment_zone\n" +
                "FROM taskCategory\n" +
                "JOIN taskEquipmentCategory ON taskCategory.taskCategoryID = taskEquipmentCategory.taskCategoryId\n" +
                "JOIN equipment ON taskEquipmentCategory.equipmentCategoryId = equipment.equipmentCategoryId\n" +
                "JOIN equipmentCategory ON equipment.equipmentCategoryId = equipmentCategory.equipmentCategoryID\n" +
                "JOIN zone ON equipment.zoneId = zone.zoneID\n" +
                "WHERE taskCategory.name =?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,nameProduct);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("equipmentID");
                String nameComponent = result.getString("name");
                String status = result.getString("status");
                String zone = result.getString("equipment_zone");
                equipmentList.add(new Equipment(id, nameComponent, status, zone));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipmentList;

    }

    public List<Component> getListOfComponentToTask(String nameProduct) {
        List<Component> components = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
            String query="SELECT component.componentID, Component.name, component.quantity \n" +
                    "FROM TaskCategory\n" +
                    "JOIN TaskCategoryComponent ON TaskCategory.taskCategoryID = TaskCategoryComponent.taskCategoryID\n" +
                    "JOIN Component ON Component.componentID = TaskCategoryComponent.componentID\n" +
                    "WHERE TaskCategory.name =?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,nameProduct);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("componentID");
                String nameComponent = result.getString("name");
                int quantity = result.getInt("quantity");
                components.add(new Component(id,nameComponent,quantity));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }
}
