package poll;

import factory.ServiceFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DiscordPollDaoSql implements DiscordPollDao {
    private static final Connection connection = ServiceFactory.getDatabaseConnection();

    @Override
    public DiscordPoll getPoll(String pollName) {
        try {
            final Statement stmt = connection.createStatement();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void createPoll(DiscordPoll poll) {

    }

    @Override
    public void setVote(String pollName, long user, int vote) {

    }
}
