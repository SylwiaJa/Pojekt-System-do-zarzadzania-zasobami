import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLDatabaseConnector {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/system";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    public MySQLDatabaseConnector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Pomyślnie połączono z bazą danych");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Employee getUserInfo(String user, String password) {
        String sqlQuery = "SELECT * FROM employee WHERE login = ? AND password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("employeeID");
                    String name = result.getString("name");
                    String lastName = result.getString("lastName");
                    int roleID = result.getInt("roleID");
                    int zoneID = result.getInt("zoneID");

                    sqlQuery = "Select roleName from role where roleID = ?";
                    try (PreparedStatement roleStatement = connection.prepareStatement(sqlQuery)) {
                        roleStatement.setInt(1, roleID);
                        try (ResultSet roleResult = roleStatement.executeQuery()) {
                            if (roleResult.next()) {
                                String roleName = roleResult.getString("roleName");

                                sqlQuery = "Select name from zone where zoneID = ?";
                                try (PreparedStatement zoneStatement = connection.prepareStatement(sqlQuery)) {
                                    zoneStatement.setInt(1, zoneID);
                                    try (ResultSet zoneResult = zoneStatement.executeQuery()) {
                                        if (zoneResult.next()) {
                                            String zoneName = zoneResult.getString("name");
                                            return new Employee(id, name, lastName,roleName,zoneName,user,password );
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Połączenie z bazą danych zostało zamknięte.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}