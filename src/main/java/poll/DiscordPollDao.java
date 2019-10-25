package poll;

public interface DiscordPollDao {
    DiscordPoll getPoll(String pollId);
    boolean createPoll(DiscordPoll poll);
    boolean setOptions(String pollId, String[] options);
    public void setVote(String pollId, long user, int option);
}
