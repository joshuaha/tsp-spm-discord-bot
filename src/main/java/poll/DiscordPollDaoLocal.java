package poll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class DiscordPollDaoLocal implements DiscordPollDao {
    /**
     * {@inheritDoc}
     */
    @Override
    public DiscordPoll getPoll(String pollName) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean createPoll(DiscordPoll poll) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setOptions(String pollId, List<String> options) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVote(String pollName, long user, int vote) {

    }
}
