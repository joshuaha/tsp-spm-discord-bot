package factory;

import poll.DiscordPollDao;
import poll.DiscordPollDaoSql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServiceFactory {
    private static final DiscordPollDao DISCORD_POLL_DAO = new DiscordPollDaoSql();

    public static DiscordPollDao getDiscordPollDao() {
        return DISCORD_POLL_DAO;
    }

    public static Connection getDatabaseConnection() {
        System.out.println("Hello");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            final Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://classdb.it.mtu.edu:3307",
                    "jhsuidge",
                    ""
            );
            return connection;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
