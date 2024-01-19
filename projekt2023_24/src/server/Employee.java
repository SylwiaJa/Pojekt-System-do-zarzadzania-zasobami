package server;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        String query="update taskstatus set endStep=CURRENT_TIMESTAMP where taskID=? and stepName='available';";
    try {
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setInt(1,task.getTaskID());
        preparedStatement.executeUpdate();
        query="insert into taskstatus (taskID, employeeID, stepName, startStep) values\n" +
                "        \t(?, ?, 'in progress', CURRENT_TIMESTAMP);";
        preparedStatement=connection.prepareStatement(query);
        preparedStatement.setInt(1,task.getTaskID());
        preparedStatement.setInt(2,id);
        preparedStatement.executeUpdate();
        query="UPDATE equipment SET status='in use' WHERE equipmentID in (SELECT equipmentID FROM taskEquipment WHERE taskID=?)";
        preparedStatement=connection.prepareStatement(query);
        preparedStatement.setInt(1,task.getTaskID());
        preparedStatement.executeUpdate();
        query="UPDATE component Set quantity=quantity-? WHERE componentID in (SELECT componentID FROM taskComponent WHERE taskID=?)";
        preparedStatement=connection.prepareStatement(query);
        preparedStatement.setInt(1,task.getQuantity());
        preparedStatement.setInt(2,task.getTaskID());
        preparedStatement.executeUpdate();
    }catch (SQLException e){
        e.printStackTrace();
    }
    }
    public void endTask(Task task, Employee employee){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        String query = "UPDATE taskStatus\n" +
                "SET endStep = CURRENT_TIMESTAMP\n" +
                "WHERE taskID =? AND stepName = 'in progress';";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, task.getTaskID());
            preparedStatement.executeUpdate();
            query = "INSERT INTO taskstatus (taskID, employeeID, stepName, startStep)\n" +
                    "VALUES (?, ?, 'finished', CURRENT_TIMESTAMP);";
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,task.getTaskID());
            preparedStatement.setInt(2,employee.getId());
            preparedStatement.executeUpdate();
            query="INSERT INTO result (resultID, quantityOK, quantityNOK)\n" +
                    "VALUES (?, ?, ?);";
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,task.getTaskID());
            preparedStatement.setInt(2,task.getQuantityOK());
            preparedStatement.setInt(3,task.getQuantityNOK());
            preparedStatement.executeUpdate();
            query="UPDATE task\n" +
                    "SET resultId = ?\n" +
                    "WHERE taskId = ?;";
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,task.getTaskID());
            preparedStatement.setInt(2,task.getTaskID());
            preparedStatement.executeUpdate();
            query="update equipment set status='available' where equipmentID in (SELECT equipmentID FROM taskequipment WHERE taskID = ?)";
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,task.getTaskID());
            preparedStatement.executeUpdate();
            query="UPDATE orderQuantity set QuantityInProduction=QuantityInProduction-?, OuantityFinished=OuantityFinished+? WHERE orderQuantityID IN (SELECT orderID from task WHERE taskID=?)";
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,task.getQuantity());
            preparedStatement.setInt(2,task.getQuantityOK());
            preparedStatement.setInt(3,task.getTaskID());
            preparedStatement.executeUpdate();
            query="UPDATE orderQuantity\n" +
                    "SET orderID = 4\n" +
                    "WHERE quantityOrdered = OuantityFinished;";
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

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
                "    equipment.equipmentID = ?\n" +
                "ORDER BY\n" +
                "    taskStatus.endStep DESC\n" +
                "LIMIT 5;\n";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            int hoursSum=0;
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                String startDate = resultSet.getString("startStep");
                String endDate = resultSet.getString("endStep");
                String taskName = resultSet.getString("taskName");
                int use = resultSet.getInt("hoursInUse");
                hoursSum+=use;
                useEquipment.add(Arrays.asList(name,lastName,startDate,endDate,taskName,use+""));
            }
            useEquipment.add(List.of(hoursSum + ""));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return useEquipment;
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
