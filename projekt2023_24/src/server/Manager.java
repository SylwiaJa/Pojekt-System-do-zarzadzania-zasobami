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

}
