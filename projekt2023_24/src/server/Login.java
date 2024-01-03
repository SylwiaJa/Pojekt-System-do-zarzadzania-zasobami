package server;

import java.sql.*;

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
        String sqlQuery = "insert into loginHistory (employeeID,startTime) values (?,CURRENT_TIME)";
        try {
            PreparedStatement statement =connection.prepareStatement(sqlQuery);
            statement.setInt(1,id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void endLogin(Employee employee){
        int id = employee.getId();
        String sqlQuery = "update loginHistory SET endTime=CURRENT_TIME where employeeID=? and endTime='0000-00-00 00:00:00'";
        try {
            PreparedStatement statement =connection.prepareStatement(sqlQuery);
            statement.setInt(1,id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
