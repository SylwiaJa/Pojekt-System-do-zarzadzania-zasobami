package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Admin extends Employee{
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/system";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;
    public Admin(int id, String name, String lastName, String role, String zone, String login, String password) {
        super(id, name, lastName, role, zone, login, password);
    }

    public Admin(int id, String name, String lastName, String role, String zone, String login, String password, Task task) {
        super(id, name, lastName, role, zone, login, password, task);
    }
    public void addRole(Employee employee, String role){

    }
    public void changeRole(Employee updateEmployee){
        MySQLDatabaseConnector mySQLDatabaseConnector = new MySQLDatabaseConnector();
        mySQLDatabaseConnector.updateEmployee(updateEmployee.getId(), updateEmployee.getRole(), updateEmployee.getZone());
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

}
