package poll;

import factory.DatabaseService;
import factory.ServiceFactory;
import org.joda.time.LocalDateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DiscordPollDaoSql implements DiscordPollDao {
    private final DatabaseService databaseService = ServiceFactory.getDatabaseService();

    /**
     * {@inheritDoc}
     */
    @Override
    public DiscordPoll getPoll(String pollId) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean createPoll(DiscordPoll poll) {
        try {
            final Connection connection = this.databaseService.getDatabaseConnection();
            final PreparedStatement stmt = connection.prepareStatement("INSERT INTO POLL VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, poll.getId());
            stmt.setLong(2, poll.getOwner());
            stmt.setString(3, poll.getText());
            stmt.setString(4, LocalDateTime.now().toString());
            stmt.setString(5, LocalDateTime.now().plusDays(1).toString());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setOptions(String pollId, String[] options) {
        try {
            final Connection connection = this.databaseService.getDatabaseConnection();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVote(String pollId, long user, int option) {

    }
}
