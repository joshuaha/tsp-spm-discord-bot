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
        // Get connection to database using the database properties file. //
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

    /**
     * Retrieves the existing database connection. This resource is shared, and closing it will cause
     * database functions to cease.
     *
     * @return the database connection
     */
    public Connection getDatabaseConnection() {
        return CONNECTION;
    }
}
