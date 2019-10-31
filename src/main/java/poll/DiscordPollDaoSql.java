package poll;

import factory.DatabaseService;
import factory.ServiceFactory;
import org.joda.time.LocalDateTime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiscordPollDaoSql implements DiscordPollDao {
    private static final String SQL_GET_POLL = "SELECT * FROM POLL WHERE ID = ?";
    private static final String SQL_CREATE_POLL = "INSERT INTO POLL (ID, OWNER, TEXT, OPEN_TIME, CLOSE_TIME) values (?, ?, ?, ?, ?)";
    private static final String SQL_GET_OPTIONS = "SELECT * FROM OPTIONS WHERE POLL_ID = ?";
    private static final String SQL_SET_OPTIONS = "INSERT INTO OPTIONS (POLL_ID, ID, TEXT) VALUES (?, ?, ?)";
    private static final String SQL_GET_VOTES = "SELECT COUNT(*) AS VOTES FROM VOTES WHERE POLL_ID = ? AND OPTION_ID = ?";
    private static final String SQL_SET_VOTE = "INSERT INTO VOTES (USER_ID, POLL_ID, OPTION_ID) VALUES (?, ?, ?)";
    private final DatabaseService databaseService = ServiceFactory.getDatabaseService();

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
            final DiscordPoll poll;
            if (set.next()) {
                poll = new DiscordPoll();
                poll.setId(set.getString("ID"));
                poll.setOwner(set.getLong("OWNER"));
                poll.setText(set.getString("TEXT"));
                poll.setOpenTime(new LocalDateTime(set.getTimestamp("OPEN_TIME")));
                poll.setCloseTime(new LocalDateTime(set.getTimestamp("CLOSE_TIME")));
            } else {
                poll = null;
            }
            set.close();
            stmt.close();
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
    public List<String> getOptions(String pollId) {
        try {
            final Connection conn = this.databaseService.getDatabaseConnection();
            final PreparedStatement stmt = conn.prepareStatement(SQL_GET_OPTIONS);
            stmt.setString(1, pollId);
            stmt.execute();
            final ResultSet set = stmt.getResultSet();
            final List<String> list = new ArrayList<>();
            while (set.next()) {
                list.add(set.getString("TEXT"));
            }
            set.close();
            stmt.close();
            return Collections.unmodifiableList(list);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
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
    public int getVotes(String pollId, int optionId) {
        try {
            final Connection conn = this.databaseService.getDatabaseConnection();
            final PreparedStatement stmt = conn.prepareStatement(SQL_GET_VOTES);
            stmt.setString(1, pollId);
            stmt.setInt(2, optionId);
            stmt.execute();
            final ResultSet set = stmt.getResultSet();
            final int votes;
            if (set.next()) {
                votes = set.getInt("VOTES");
            } else {
                votes = -1;
            }
            set.close();
            stmt.close();
            return votes;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setVote(long userId, String pollId, int optionId) {
        try {
            final Connection conn = this.databaseService.getDatabaseConnection();
            final PreparedStatement stmt = conn.prepareStatement(SQL_SET_VOTE);
            stmt.setLong(1, userId);
            stmt.setString(2, pollId);
            stmt.setInt(3, optionId);
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
