package poll;

import factory.DatabaseService;
import factory.ServiceFactory;
import org.joda.time.LocalDateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiscordPollDaoSql implements DiscordPollDao {
    private static final String SQL_GET_POLL = "SELECT ID, OWNER_ID, TEXT, OPEN_TIME, CLOSE_TIME, SERVER_ID, CHANNEL_ID, MESSAGE_ID FROM POLL WHERE ID = ?";
    private static final String SQL_CREATE_POLL = "INSERT INTO POLL (ID, OWNER_ID, TEXT, OPEN_TIME, CLOSE_TIME, SERVER_ID, CHANNEL_ID, MESSAGE_ID) values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_POLL = "UPDATE POLL SET OWNER_ID = ?, TEXT = ?, OPEN_TIME = ?, CLOSE_TIME = ?, SERVER_ID = ?, CHANNEL_ID = ?, MESSAGE_ID = ? WHERE ID = ?";
    private static final String SQL_GET_OPTIONS = "SELECT * FROM OPTION WHERE POLL_ID = ?";
    private static final String SQL_SET_OPTIONS = "INSERT INTO OPTION (POLL_ID, ID, TEXT) VALUES (?, ?, ?)";
    private static final String SQL_REMOVE_OPTIONS = "DELETE FROM OPTION WHERE POLL_ID = ?";
    private static final String SQL_GET_VOTES = "SELECT COUNT(*) AS VOTES FROM VOTE WHERE POLL_ID = ? AND OPTION_ID = ?";
    private static final String SQL_SET_VOTE = "INSERT INTO VOTE (POLL_ID, USER_ID, OPTION_ID) VALUES (?, ?, ?)";
    private static final String SQL_REMOVE_VOTE = "DELETE FROM VOTE WHERE POLL_ID = ? AND USER_ID = ?";
    private static final String SQL_REMOVE_VOTES = "DELETE FROM VOTE WHERE POLL_ID = ?";
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
                poll.setId(set.getString(1));
                poll.setOwnerId(set.getLong(2));
                poll.setText(set.getString(3));
                poll.setOpenTime(new LocalDateTime(set.getTimestamp(4)));
                poll.setCloseTime(new LocalDateTime(set.getTimestamp(5)));
                poll.setServerId(set.getLong(6));
                poll.setChannelId(set.getLong(7));
                poll.setMessageId(set.getLong(8));
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
            stmt.setLong(2, poll.getOwnerId());
            stmt.setString(3, poll.getText());
            stmt.setString(4, LocalDateTime.now().toString());
            stmt.setString(5, LocalDateTime.now().plusDays(1).toString());
            stmt.setLong(6, poll.getServerId());
            stmt.setLong(7, poll.getChannelId());
            stmt.setLong(8, poll.getMessageId());
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
    public boolean updatePoll(DiscordPoll poll) {
        try {
            final Connection conn = this.databaseService.getDatabaseConnection();
            final PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_POLL);
            stmt.setLong(1, poll.getOwnerId());
            stmt.setString(2, poll.getText());
            stmt.setString(3, poll.getOpenTime().toString());
            stmt.setString(4, poll.getCloseTime().toString());
            stmt.setString(5, poll.getId());
            stmt.setLong(6, poll.getServerId());
            stmt.setLong(7, poll.getChannelId());
            stmt.setLong(8, poll.getMessageId());
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
        this.removeVotes(pollId);
        this.removeOptions(pollId);
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
    public boolean removeOptions(String pollId) {
        try {
            final Connection conn = this.databaseService.getDatabaseConnection();
            final PreparedStatement stmt = conn.prepareStatement(SQL_REMOVE_OPTIONS);
            stmt.setString(1, pollId);
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException ex) {
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
    public boolean setVote(String pollId, long userId, int optionId) {
        try {
            final Connection conn = this.databaseService.getDatabaseConnection();
            final PreparedStatement stmt = conn.prepareStatement(SQL_SET_VOTE);
            stmt.setString(1, pollId);
            stmt.setLong(2, userId);
            stmt.setInt(3, optionId);
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
    public boolean removeVote(String pollId, long userId) {
        try {
            final Connection conn = this.databaseService.getDatabaseConnection();
            final PreparedStatement stmt = conn.prepareStatement(SQL_REMOVE_VOTE);
            stmt.setString(1, pollId);
            stmt.setLong(2, userId);
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
    public boolean removeVotes(String pollId) {
        try {
            final Connection conn = this.databaseService.getDatabaseConnection();
            final PreparedStatement stmt = conn.prepareStatement(SQL_REMOVE_VOTES);
            stmt.setString(1, pollId);
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
