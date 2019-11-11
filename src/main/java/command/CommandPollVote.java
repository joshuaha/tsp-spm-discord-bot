package command;

import factory.ServiceFactory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.joda.time.LocalDateTime;
import poll.DiscordPoll;
import poll.DiscordPollDao;

import java.util.List;

public class CommandPollVote implements Command {
    private final DiscordPollDao pollDao = ServiceFactory.getDiscordPollDao();

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAlias() {
        return "vote";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        final DiscordPoll poll = this.pollDao.getPoll(args[0]);
        final LocalDateTime currentTime = LocalDateTime.now();
        final LocalDateTime openTime = poll.getOpenTime();
        final LocalDateTime closeTime = poll.getCloseTime();
        //ensure that poll is open
        if (openTime.compareTo(currentTime) <= 0 && currentTime.compareTo(closeTime) <= 0) {
            final long userId = event.getAuthor().getIdLong();
            final int vote = Integer.parseInt(args[1]) - 1;
            final boolean success = this.pollDao.removeVote(poll.getId(), userId) && this.pollDao.setVote(poll.getId(), userId, vote);
            if (success) {
                final List<String> options = this.pollDao.getOptions(poll.getId());
                final List<Integer> votes = this.pollDao.getVotes(poll.getId());
                final String display = DiscordPoll.getDisplayMessage(poll, options, votes);
                event.getChannel().editMessageById(poll.getMessageId(), display).queue();
            }
        }
    }
}