package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Manager extends Leader{
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/system";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;
    public Manager(int id, String name, String lastName, String role, String zone, String login, String password) {
        super(id, name, lastName, role, zone, login, password);
    }

    public Manager(int id, String name, String lastName, String role, String zone, String login, String password, Task task) {
        super(id, name, lastName, role, zone, login, password, task);
    }
    public void addTaskCategory(String category){

    }
    public void addNewTask(String name, String priority, String description, int norm, Component component ){

    }
    public void addTaskPriority(Task task, String priority){

    }
    public void changeTaskPriority(Task task, String  priority){

    }
    public void addLicenseToEmployee(Employee employee, License license){

    }
    public void addEquipment(Equipment equipment){

    }
    public void changeEquipmentStatus(Equipment equipment, String status){

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
                "    `order` o ON oq.orderID = o.OrderID;\n";
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
}
