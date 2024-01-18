package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
    public List<List<String>> getEquipmentTimeOfUseReport(int id){
        List<List<String>> useEquipment = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT\n" +
                "    employee.name,\n" +
                "    employee.lastName,\n" +
                "    taskStatus.startStep,\n" +
                "    taskStatus.endStep,\n" +
                "    task.name as taskName,\n" +
                "    TIMESTAMPDIFF(HOUR, taskStatus.startStep, taskStatus.endStep) AS hoursInUse\n" +
                "FROM\n" +
                "    equipment\n" +
                "JOIN taskEquipment ON taskEquipment.equipmentID = equipment.equipmentID\n" +
                "JOIN task ON task.taskID = taskEquipment.taskID\n" +
                "JOIN taskStatus ON taskStatus.taskID = task.taskID\n" +
                "JOIN employee ON employee.employeeID = taskStatus.employeeID\n" +
                "WHERE\n" +
                "    equipment.equipmentID = ?;\n";

try{
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1,id);
    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()){
        String name = resultSet.getString("name");
        String lastName = resultSet.getString("lastName");
        String startDate = resultSet.getString("startStep");
        String endDate = resultSet.getString("endStep");
        String taskName = resultSet.getString("taskName");
        int use = resultSet.getInt("hoursInUse");
        useEquipment.add(Arrays.asList(name,lastName,startDate,endDate,taskName,use+""));
    }
    for (List<String> innerList : useEquipment) {
        for (String value : innerList) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}catch (SQLException e){
    e.printStackTrace();
}
return useEquipment;
    }
    public void addTaskPriority(Task task, String priority){

    }
    public void changeTaskPriority(Task task, String  priority){

    }
    public void addLicenseToEmployee(Employee employee, License license){

    }
    public void addEquipment(Equipment equipment){

    }
    public void changeEquipmentStatus(Equipment equipment){
        int id = equipment.getId();
        String status = equipment.getStatus();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "update equipment set status=? where equipmentID=?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,status);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public List<List<String>> getEmpInfo(Employee employee){
        List<List<String>> empInfo = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "select concat(e.name,' ',e.lastName) 'employee', z.name 'zone', \n" +
                "\t\tt.name 'task', t.description,\n" +
                "        ts.stepName 'status', ts.startStep, TIMESTAMPDIFF(HOUR, ts.startStep, CURRENT_TIMESTAMP) 'hours'\n" +
                "from employee e left join zone z on e.zoneID=z.zoneID\n" +
                "\t\t\t\tleft join taskstatus ts on ts.employeeID=e.employeeID\n" +
                "                left join task t on t.taskID=ts.taskID\n" +
                "where ts.endStep ='0000-00-00 00:00:00' AND ts.employeeID=?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,employee.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String task = resultSet.getString("task");
                String description = resultSet.getString("description");
                String status = resultSet.getString("status");
                String startStep = resultSet.getString("startStep");
                String hours = resultSet.getString("hours");
                List<String> info = new ArrayList<>();
                info.add(task);
                info.add(description);
                info.add(status);
                info.add(startStep);
                info.add(hours);
                empInfo.add(info);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  empInfo;
    }
    public List<String> getTaskInfo(Task task){
        List<String> taskInfo = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT t.taskID, t.name, t.priority, t.description, p.name 'product', t.quantity, t.norm, \n" +
                "\t\tts.stepName 'status',\n" +
                "        concat(e.name,' ',e.lastName) 'employee',\n" +
                "        GROUP_CONCAT(DISTINCT eq.name) 'equipment' , GROUP_CONCAT(DISTINCT c.name) 'component'\n" +
                "FROM task t join product p on p.productID=t.productID \n" +
                "\t\t\tjoin taskstatus ts on ts.taskID=t.taskID \n" +
                "            join employee e on ts.employeeID=e.employeeID\n" +
                "            join taskequipment te  on te.taskID=t.taskID\n" +
                "            join equipment eq on eq.equipmentID=te.equipmentID\n" +
                "            join taskcomponent tc  on tc.taskID=t.taskID\n" +
                "            join component c on c.componentID=tc.componentID    \n" +
                "            WHERE ts.endStep='0000-00-00 00:00:00' AND t.taskID=?\n" +
                "GROUP BY t.taskID;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,task.getTaskID());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int taskID = resultSet.getInt("taskID");
                String name = resultSet.getString("name");
                String priority = resultSet.getString("priority");
                String description = resultSet.getString("description");
                String product = resultSet.getString("product");
                int quantity = resultSet.getInt("quantity");
                int norm = resultSet.getInt("norm");
                String status = resultSet.getString("status");
                String employee = resultSet.getString("employee");
                String equipment = resultSet.getString("equipment");
                String component = resultSet.getString("component");
                taskInfo.add(String.valueOf(taskID));
                taskInfo.add(name);
                taskInfo.add(priority);
                taskInfo.add(description);
                taskInfo.add(product);
                taskInfo.add(String.valueOf(quantity));
                taskInfo.add(String.valueOf(norm));
                taskInfo.add(status);
                taskInfo.add(employee);
                taskInfo.add(equipment);
                taskInfo.add(component);
                query="SELECT \n" +
                        "    SEC_TO_TIME(SUM(total_duration)) AS total_duration\n" +
                        "FROM (\n" +
                        "    SELECT \n" +
                        "        TIME_TO_SEC(TIMEDIFF(IF(endStep = '0000-00-00 00:00:00', CURRENT_TIMESTAMP, endStep), startStep)) AS total_duration\n" +
                        "    FROM taskStatus\n" +
                        "    WHERE taskID = ? AND stepName IN ('available', 'in progress')\n" +
                        ") AS subquery;\n";
                preparedStatement=connection.prepareStatement(query);
                preparedStatement.setInt(1,task.getTaskID());
                resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    String time = resultSet.getString("total_duration");
                    taskInfo.add(time);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  taskInfo;
    }
}
