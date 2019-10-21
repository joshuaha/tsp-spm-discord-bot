package poll;

public class DiscordPollDaoSql implements DiscordPollDao {
    @Override
    public DiscordPoll getPoll(String pollName) {
        return null;
    }

    @Override
    public void createPoll(DiscordPoll poll) {

    }

    @Override
    public void setVote(String pollName, long user, int vote) {

    }
}
