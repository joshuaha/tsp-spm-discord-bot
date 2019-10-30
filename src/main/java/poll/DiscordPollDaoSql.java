package poll;

import factory.DatabaseService;
import factory.ServiceFactory;
import org.joda.time.LocalDateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DiscordPollDaoSql implements DiscordPollDao {
    private final DatabaseService databaseService = ServiceFactory.getDatabaseService();
    private static final String SQL_GET_POLL = "SELECT * FROM POLL WHERE ID = ?";
    private static final String SQL_CREATE_POLL = "INSERT INTO POLL (ID, OWNER, TEXT, OPEN_TIME, CLOSE_TIME) values (?, ?, ?, ?, ?)";
    private static final String SQL_SET_OPTIONS = "INSERT INTO OPTIONS (POLL_ID, ID, TEXT) VALUES (?, ?, ?)";

    /**
     * {@inheritDoc}
     */
    @Override
    public DiscordPoll getPoll(String pollId) {
        try {
            final Connection conn = this.databaseService.getDatabaseConnection();
            final PreparedStatement stmt = conn.prepareStatement(SQL_GET_POLL);
            stmt.setString(1, pollId);
            stmt.execute();
            final ResultSet set = stmt.getResultSet();
            final DiscordPoll poll = new DiscordPoll();
            set.next();
            poll.setId(set.getString("ID"));
            poll.setOwner(set.getLong("OWNER"));
            poll.setText(set.getString("TEXT"));
            poll.setOpenTime(new LocalDateTime(set.getTimestamp("OPEN_TIME")));
            poll.setCloseTime(new LocalDateTime(set.getTimestamp("CLOSE_TIME")));
            set.close();
            stmt.close();
            conn.close();
            return poll;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean createPoll(DiscordPoll poll) {
        try {
            final Connection conn = this.databaseService.getDatabaseConnection();
            final PreparedStatement stmt = conn.prepareStatement(SQL_CREATE_POLL);
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
            final PreparedStatement stmt = conn.prepareStatement(SQL_SET_OPTIONS);
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
            ex.printStackTrace();
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
