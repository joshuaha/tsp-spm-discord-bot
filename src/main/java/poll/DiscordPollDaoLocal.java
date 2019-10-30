package poll;

import java.util.List;

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
     *
     * @return
     */
    @Override
    public boolean setVote(long user, String pollName, int vote) {
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public int getVotes(String pollId, int optionId) {
        return 0;
    }
}
