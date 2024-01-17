package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductionEmployee extends Employee{
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/system";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;
    public ProductionEmployee(int id, String name, String lastName, String role, String zone, String login, String password) {
        super(id, name, lastName, role, zone, login, password);
    }

    public ProductionEmployee(int id, String name, String lastName, String role, String zone, String login, String password, Task task) {
        super(id, name, lastName, role, zone, login, password, task);
    }

    public Task getMyTask(Employee employee)
    {
        Task task = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT \n" +
                "    task.taskID,\n" +
                "    MAX(task.name) as taskName,\n" +
                "    MAX(task.priority) as taskPriority,\n" +
                "    MAX(task.description) as taskDescription,\n" +
                "    MAX(taskCategory.name) as nameCategory,\n" +
                "    MAX(task.norm) as taskNorm,\n" +
                "    MAX(result.quantityOK) as quantityOK,\n" +
                "    MAX(result.quantityNOK) as quantityNOK,\n" +
                "    MAX(taskStatus.stepName) as status,\n" +
                "    MAX(product.name) as productName,\n" +
                "    MAX(task.quantity) as taskQuantity,\n" +
                "    MAX(zone.name) as zoneName,\n" +
                "    MAX(equipment.name) as equipmentName,\n" +
                "    GROUP_CONCAT(component.name) as componentName \n" +
                "FROM \n" +
                "    task\n" +
                "JOIN \n" +
                "    taskStatus ON taskStatus.taskID = task.taskID\n" +
                "JOIN \n" +
                "    taskCategory ON taskCategory.taskCategoryID = task.taskCategory\n" +
                "JOIN \n" +
                "    result ON result.resultID = task.resultID\n" +
                "JOIN \n" +
                "    product ON product.productID = task.productID\n" +
                "JOIN \n" +
                "    zone ON zone.zoneID = task.zoneID\n" +
                "JOIN \n" +
                "    taskEquipment ON taskEquipment.taskID = task.taskID\n" +
                "JOIN \n" +
                "    equipment ON equipment.equipmentID = taskEquipment.equipmentID\n" +
                "JOIN \n" +
                "    taskComponent ON taskComponent.taskID = task.taskID\n" +
                "JOIN \n" +
                "    component ON component.componentID = taskComponent.componentID\n" +
                "WHERE \n" +
                "    taskStatus.employeeID = ?\n" +
                "    AND taskStatus.stepName = 'in progress' \n" +
                "    AND taskStatus.endStep = '0000-00-00 00-00-00'\n" +
                "GROUP BY \n" +
                "    task.taskID;\n";
        try{
            int employeeID = employee.getId();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,employeeID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int taskID = resultSet.getInt("taskID");
                String taskName = resultSet.getString("taskName");
                String priority = resultSet.getString("taskPriority");
                String description = resultSet.getString("taskDescription");
                String category = resultSet.getString("nameCategory");
                int norm = resultSet.getInt("taskNorm");
                int quantityOK = resultSet.getInt("quantityOK");
                int quantityNOK = resultSet.getInt("quantityNOK");
                String status = resultSet.getString("status");
                String product = resultSet.getString("productName");
                int quantity = resultSet.getInt("taskQuantity");
                String zone = resultSet.getString("zoneName");
                String equipment = resultSet.getString("equipmentName");
                String component = resultSet.getString("componentName");
                task = new Task(taskID,taskName,priority,description,category,norm,quantityOK,quantityNOK,status,product,quantity,zone,equipment,component);


            }
        }catch (SQLException e){
            e.printStackTrace();
        }
return task;
    }
    public List<Task> getListOfTask(int employeeID) {
        List<Task> tasks = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT t.taskID, t.name, t.priority, t.description, p.name AS 'product', t.quantity, t.norm,\n" +
                "       ts.stepName AS 'status',\n" +
                "       GROUP_CONCAT(DISTINCT eq.name) AS 'equipment', GROUP_CONCAT(DISTINCT c.name) AS 'component'\n" +
                "FROM task t\n" +
                "         JOIN product p ON p.productID = t.productID\n" +
                "         JOIN taskstatus ts ON ts.taskID = t.taskID\n" +
                "         JOIN taskequipment te ON te.taskID = t.taskID\n" +
                "         JOIN equipment eq ON eq.equipmentID = te.equipmentID\n" +
                "         JOIN taskcomponent tc ON tc.taskID = t.taskID\n" +
                "         JOIN component c ON c.componentID = tc.componentID\n" +
                "WHERE\n" +
                "  -- zadanie jest dostępne:\n" +
                "    ts.endStep = '0000-00-00 00:00:00' AND ts.stepName = 'available'\n" +
                "  -- uprawnienie na zadanie:\n" +
                "  AND t.taskCategory IN (SELECT DISTINCT taskcategoryid FROM taskcategorylicense WHERE licenseID IN\n" +
                "                                                                                       (SELECT licenseId FROM employeelicense WHERE employeeId = ? AND expirationDate >= CURRENT_DATE))\n" +
                "  -- uprawnienie na sprzęt:\n" +
                "  AND eq.equipmentCategoryID IN (SELECT equipmentcategoryId FROM equipmentcategorylicense WHERE licenseID IN\n" +
                "                                                                                                (SELECT licenseId FROM employeelicense WHERE employeeId = ? AND expirationDate >= CURRENT_DATE))\n" +
                "  -- componenty są dostępne:\n" +
                "  AND (\n" +
                "          SELECT COUNT(*) = SUM(CASE WHEN tc.quantity <= c.quantity THEN 1 ELSE 0 END)\n" +
                "          FROM taskcomponent tc\n" +
                "                   JOIN component c ON tc.componentid = c.componentid\n" +
                "          WHERE tc.taskid = t.taskid\n" +
                "      ) > 0\n" +
                "\n" +
                "  -- sprzęt jest dostępny:\n" +
                "  AND (\n" +
                "          SELECT COUNT(*) = SUM(CASE WHEN e.status = 'available' THEN 1 ELSE 0 END)\n" +
                "          FROM equipment e\n" +
                "                   JOIN taskequipment te ON e.equipmentID = te.equipmentID\n" +
                "          WHERE te.taskId = t.taskid\n" +
                "      ) > 0\n" +
                "\n" +
                "GROUP BY t.taskID;\n";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeID);
            preparedStatement.setInt(2,employeeID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int taskID = resultSet.getInt("taskID");
                String name = resultSet.getString("name");
                String priority = resultSet.getString("priority");
                String description = resultSet.getString("description");
                String productName = resultSet.getString("product");
                int quantity = resultSet.getInt("quantity");
                int norm = resultSet.getInt("norm");
                String status = resultSet.getString("status");
                String equipment = resultSet.getString("equipment");
                String component = resultSet.getString("component");
                tasks.add(new Task(taskID, name, priority, description, productName, quantity, norm, status, equipment, component));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
