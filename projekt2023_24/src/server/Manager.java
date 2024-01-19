package server;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

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
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "insert into taskCategory (name ) values (?) ";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,category);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void addNewTask(Task task, Employee employee){
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
                    int employeeID = employee.getId();
                    query="insert into taskStatus(taskID,employeeID,stepName,startStep) values (?,?,'available',CURRENT_TIMESTAMP)";
                    preparedStatement=connection.prepareStatement(query);
                    preparedStatement.setInt(1,id);
                    preparedStatement.setInt(2,employeeID);
                    preparedStatement.executeUpdate();

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
    public void addLicenseToEmployee(List<String> list ){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "insert into employeelicense (employeeID, licenseID, startDate, expirationDate)\n" +
                "        values ((select employeeID from employee where name=? AND lastName=?), (select licenseID from license where name=? ), ?, ?);";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            List<String> name = Arrays.asList(list.get(0).split(" "));
            preparedStatement.setString(1,name.get(0));
            preparedStatement.setString(2,name.get(1));
            preparedStatement.setString(3,list.get(1));
            preparedStatement.setString(4,list.get(2));
            preparedStatement.setString(5,list.get(3));
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void addEquipment(String s1,String s2,String s3,String s4 ){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "insert into equipment (name, equipmentCategoryID, status, zoneID) values (?, (select equipmentCategoryID from equipmentCategory where name=? ), ?, (select zoneID from zone where name=?));";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,s1);
            preparedStatement.setString(2,s2);
            preparedStatement.setString(3,s3);
            preparedStatement.setString(4,s4);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
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

    public List<String> getListOfTaskCategory() {
        List<String> taskCategory = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        String query = "SELECT taskCategory.name FROM taskCategory";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                String name = result.getString("name");
                taskCategory.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskCategory;
    }

    public List<String> getListofEquipmentCategory() {
        List<String> eqCategory = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        String query = "SELECT equipmentCategory.name FROM equipmentCategory";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                String name = result.getString("name");
                eqCategory.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eqCategory;
    }

    public List<License> getLicenseList() {
        List<License> licenseList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        String query = "SELECT *  from license";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("licenseID");
                String name = result.getString("name");
                String description = result.getString("description");
                licenseList.add(new License(id, name,description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return licenseList;
    }

    public List<String> getListOfZone() {
        List<String> zone = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        String query = "SELECT zone.name from zone";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                String name = result.getString("name");
                zone.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zone;
    }

    public void addLicenseToTaskCategory(String s, String s1) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "insert into taskcategorylicense values ((select taskCategoryID from taskCategory where name = ?), (select licenseID from license where name=?));";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
preparedStatement.setString(1,s);
preparedStatement.setString(2,s1);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addEquipmentToTaskCategory(String s, String s1) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "insert into taskequipmentcategory values ((select taskCategoryID from taskCategory where name = ?), (select equipmentCategoryID from equipmentCategory where name=?) );";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,s);
            preparedStatement.setString(2,s1);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
