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
    public void addNewTask(Task task ){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT taskCategory.taskCategoryID\n" +
                "FROM taskCategory\n" +
                "WHERE taskCategory.name = (SELECT product.name FROM product WHERE product.productID = ?); ";
        int productID = task.getProductID();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,productID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int taskCategoryID = resultSet.getInt("taskCategoryID");
                query = "select zoneID from zone where name=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, task.getEquipment().getZone());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    int zoneID = resultSet.getInt("zoneID");
                    query = "insert into task (name,priority,description, taskCategory,norm,productID,quantity,orderID,zoneID) values (?,?,?,?,?,?,?,?,?);";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, task.getName());
                preparedStatement.setString(2, task.getPriority());
                preparedStatement.setString(3, task.getDescription());
                preparedStatement.setInt(4, taskCategoryID);
                preparedStatement.setInt(5, task.getNorm());
                preparedStatement.setInt(6, task.getProductID());
                preparedStatement.setInt(7, task.getQuantity());
                preparedStatement.setInt(8, task.getOrderID());
                preparedStatement.setInt(9,zoneID);
                preparedStatement.executeUpdate();
                query = "SELECT taskID\n" +
                        "FROM task\n" +
                        "ORDER BY taskID DESC\n" +
                        "LIMIT 1;\n";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("taskID");
                    query = "UPDATE orderQuantity SET QuantityInProduction = QuantityInProduction + ?, orderID=2 WHERE orderQuantityID = ?;";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, task.getQuantity());
                    preparedStatement.setInt(2, task.getOrderID());
                    preparedStatement.executeUpdate();
                    query = "insert into taskEquipment(taskID, equipmentID) values (?,?)";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setInt(2, task.getEquipment().getId());
                    preparedStatement.executeUpdate();
                    for (int i = 0; i < task.getComponent().size(); i++) {
                        query = "insert into taskComponent(taskID, componentID,quantity)values (?,?,?)";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, id);
                        preparedStatement.setInt(2, task.getComponent().get(i).getId());
                        preparedStatement.setInt(3, task.getQuantity());
                        preparedStatement.executeUpdate();
                    }

                }
            } }
        }catch (SQLException e){
            e.printStackTrace();
        }
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


}
