package poll;

import java.util.List;

public interface DiscordPollDao {
    DiscordPoll getPoll(String pollId);
    boolean createPoll(DiscordPoll poll);
    boolean setOptions(String pollId, List<String> options);
    public void setVote(String pollId, long user, int option);
}
