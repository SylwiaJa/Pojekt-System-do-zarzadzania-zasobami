package server;
import java.util.Date;

import java.sql.*;
import java.text.SimpleDateFormat;

public class Login {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/system";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;
public Login(){
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        System.out.println("Pomyślnie połączono z bazą danych");
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
}
    public boolean check(String login, String password){

        String sqlQuery = "SELECT * FROM employee WHERE login = ? AND password = ?";
            boolean flag = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet result = preparedStatement.executeQuery();
           if( result.next())
               flag=true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return  flag;
    }
    public void  startLogin(Employee employee){
    int id = employee.getId();
        Date data = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataFormat = format.format(data);
        employee.setStartTime(dataFormat);

        String sqlQuery = "insert into loginHistory (employeeID,startTime) values (?,?)";
        try {
            PreparedStatement statement =connection.prepareStatement(sqlQuery);
            statement.setInt(1,id);
            statement.setString(2,dataFormat);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void endLogin(Employee employee){
        int id = employee.getId();
        Date data = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataFormat = format.format(data);
        String startTime = employee.getStartTime();

        String sqlQuery = "update loginHistory SET startTime=?,endTime=? where employeeID=? AND endTime='0000-00-00 00:00:00'";
        try {
            PreparedStatement statement =connection.prepareStatement(sqlQuery);
            statement.setString(1,startTime);
            statement.setString(2,dataFormat);
            statement.setInt(3,id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
