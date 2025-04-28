import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private  static String dbURL = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:init.sql'";
    private  static String dbUsername = "";
    private  static String dbPassword = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("H2 driver is not found", e);
        }
        Connection connection = null;
        connection = DriverManager.getConnection(dbURL, dbUsername,dbPassword);
        return connection;
    }
}
