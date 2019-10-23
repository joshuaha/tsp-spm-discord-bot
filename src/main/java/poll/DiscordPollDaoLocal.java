package poll;

import java.util.HashMap;
import java.util.Map;

public class DiscordPollDaoLocal implements DiscordPollDao {
    private static final Map<String, DiscordPoll> POLLS = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public DiscordPoll getPoll(String pollName) {
        return POLLS.get(pollName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean createPoll(DiscordPoll poll) {
        POLLS.put(poll.getId(), poll);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setOptions(String pollId, String[] options) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVote(String pollName, long user, int vote) {
        //POLLS_OLD.get(pollName).setVote(user, vote);
    }
}
