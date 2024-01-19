package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Leader extends Employee {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/system";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;
    public Leader(int id, String name, String lastName, String role, String zone, String login, String password) {
        super(id, name, lastName, role, zone, login, password);
    }

    public Leader(int id, String name, String lastName, String role, String zone, String login, String password, Task task) {
        super(id, name, lastName, role, zone, login, password, task);
    }



    public List<Employee> getListOfEmployeeAndTask() {
return  null;
    }

    public void getTimeReport() {

    }

    public void getZoneReport() {

    }
public void getEmployeeReport(){

}
public void getEquipmentStatusReport(){

}
public void getEquipmentTimeOfUseReport(){

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
                if(zone.equals(this.getZone())) {
                    employees.add(new Employee(id, name, lastName, role, zone));
                }
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
                if(zoneName.equals(this.getZone())) {
                    tasks.add(new Task(id, name, priority, description, norm, zoneName, quantity));
                }
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
                if(zoneName.equals(this.getZone())) {
                    equipments.add(new Equipment(id, name, status, zoneName));
                }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return equipments;
    }
}