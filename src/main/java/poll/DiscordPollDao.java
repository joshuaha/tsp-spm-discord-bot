package poll;

import java.util.List;

public interface DiscordPollDao {
    DiscordPoll getPoll(String pollId);
    boolean createPoll(DiscordPoll poll);
    boolean setOptions(String pollId, List<String> options);
    boolean setVote(long user, String pollId, int optionId);
    int getVotes(String pollId, int optionId);
}
