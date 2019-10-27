package poll;

import factory.DatabaseService;
import factory.ServiceFactory;
import org.joda.time.LocalDateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            final Connection conn = this.databaseService.getDatabaseConnection();
            final PreparedStatement stmt = conn.prepareStatement("INSERT INTO POLL VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, poll.getId());
            stmt.setLong(2, poll.getOwner());
            stmt.setString(3, poll.getText());
            stmt.setString(4, LocalDateTime.now().toString());
            stmt.setString(5, LocalDateTime.now().plusDays(1).toString());
            stmt.execute();
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setOptions(String pollId, List<String> options) {
        try {
            final Connection conn = this.databaseService.getDatabaseConnection();
            final PreparedStatement stmt = conn.prepareStatement("INSERT INTO OPTIONS VALUES (?, ?, ?)");
            for (int i = 0; i < options.size(); i++) {
                stmt.setString(1, pollId);
                stmt.setInt(2, i);
                stmt.setString(3, options.get(i));
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.close();
            conn.close();
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
