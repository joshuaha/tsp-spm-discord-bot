package poll;

public interface DiscordPollDao {
    DiscordPoll getPoll(String pollName);
    void createPoll(DiscordPoll poll) ;
    void setVote(String pollName, long user, int vote);
}
