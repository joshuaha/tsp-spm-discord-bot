package poll;

import java.util.List;

public interface DiscordPollDao {
    /**
     * Gets data about a poll with the given ID from the database.
     *
     * @param pollId the poll ID
     * @return an object representing a poll with the given poll ID
     */
    DiscordPoll getPoll(String pollId);

    /**
     * Creates a poll with the specified properties.
     *
     * @param poll the poll object
     * @return whether the poll was created successfully
     */
    boolean createPoll(DiscordPoll poll);

    /**
     * Gets a list of options for the poll with the given ID.
     *
     * @param pollId the poll ID
     * @return a list of options for the poll with the given ID
     */
    List<String> getOptions(String pollId);

    /**
     * Sets the list of options for the poll with the given ID.
     *
     * @param pollId  the poll ID
     * @param options the list of options
     * @return whether the options were set successfully
     */
    boolean setOptions(String pollId, List<String> options);

    /**
     * Sets the vote for a specific user on a specific poll.
     *
     * @param user     the user ID
     * @param pollId   the poll ID
     * @param optionId the option ID
     * @return whether the vote was set successfully
     */
    boolean setVote(long user, String pollId, int optionId);

    /**
     * Gets the number of votes which have been placed for the specified poll option.
     *
     * @param pollId   the poll ID
     * @param optionId the option ID
     * @return the number of votes the option has received or {@code -1} if unsuccessful
     */
    int getVotes(String pollId, int optionId);
}
