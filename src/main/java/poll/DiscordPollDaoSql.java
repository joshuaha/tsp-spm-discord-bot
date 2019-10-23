package poll;

public class DiscordPollDaoSql implements DiscordPollDao {
    @Override
    public DiscordPollOld getPoll(String pollName) {
        return null;
    }

    @Override
    public void createPoll(DiscordPollOld poll) {

    }

    @Override
    public void setVote(String pollName, long user, int vote) {

    }
}
