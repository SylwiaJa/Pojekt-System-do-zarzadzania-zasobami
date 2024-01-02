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

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            try (ResultSet result = preparedStatement.executeQuery()) {
                    if (result ==null)
                        return false;
                    else
                        return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public void  startLogin(Employee employee){
    int id = employee.getId();
        String sqlQuery = "insert into loginHistory (employeeID,startTime) values (?,CURRENT_TIMESTAMP)";
        try {
            PreparedStatement statement =connection.prepareStatement(sqlQuery);
            statement.setInt(1,id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void endLogin(Employee employee){

    }
}
