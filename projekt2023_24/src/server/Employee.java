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
    public  List<Task> getListOfTasks(){
        return null;
    };
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
                "    `order` o ON oq.orderID = o.OrderID\n";
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()){
                int id = result.getInt("orderQuantityID");
                String productName = result.getString("productName");
                String orderStatus = result.getString("orderStatus");
                int quantityOrdered = result.getInt("quantityOrdered");
                int quantityInProduction = result.getInt("QuantityInProduction");
                int quantityFinished = result.getInt("OuantityFinished");
                orders.add(new Order(id, orderStatus,new Product(productName,quantityOrdered,quantityInProduction,quantityFinished)));
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

    public void setRole(String role) {
        this.role = role;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
