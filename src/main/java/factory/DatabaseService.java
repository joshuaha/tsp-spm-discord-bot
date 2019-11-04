package factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseService {
    private static final Connection CONNECTION;

    static {
        Connection connection;
        try {
            final Properties properties = new Properties();
            properties.load(new FileInputStream("database.properties"));
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    properties.getProperty("database"),
                    properties.getProperty("username"),
                    properties.getProperty("password")
            );
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            connection = null;
        }
        CONNECTION = connection;
    }

    public Connection getDatabaseConnection() {
        return CONNECTION;
    }
}
