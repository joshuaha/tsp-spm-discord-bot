package command;

import com.mysql.jdbc.TimeUtil;
import factory.ServiceFactory;
import groovyjarjarantlr.debug.Event;
import groovyjarjarantlr.debug.MessageEvent;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.collections4.keyvalue.TiedMapEntry;
import org.joda.time.LocalDateTime;
import poll.DiscordPoll;
import poll.DiscordPollDao;
import poll.DiscordPollFormatter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandPollVote extends CommandAbstract implements Command {
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
        // Ensures that poll is open when you vote on it. //
        if (openTime.compareTo(currentTime) <= 0 && currentTime.compareTo(closeTime) <= 0) {
            final long userId = event.getAuthor().getIdLong();
            final int vote = Integer.parseInt(args[1]) - 1;
            final boolean channelVoteSync = channelVoteSyncCheck(poll, event);
            boolean success = false;
            if (channelVoteSync)
                success = this.pollDao.removeVote(poll.getId(), userId) && this.pollDao.setVote(poll.getId(), userId, vote);
            else {
                Message x = event.getChannel().sendMessage("Unable to cast vote. Be sure to vote in the same channel as the poll.").complete();
                deleteMessage(x);
            }
            if (success) {
                final List<String> options = this.pollDao.getOptions(poll.getId());
                final List<Integer> votes = this.pollDao.getVotes(poll.getId());
                final String display = DiscordPollFormatter.getDisplayMessage(poll, options, votes);
                event.getChannel().editMessageById(poll.getMessageId(), display).queue();
            }
        }
    }
    // Ensures that votes cannot occur from outside the server and channel that the poll was started in. //
    public boolean channelVoteSyncCheck(DiscordPoll poll, MessageReceivedEvent event) {
        return Long.parseLong(event.getChannel().getId()) == poll.getChannelId() && Long.parseLong(event.getGuild().getId()) == poll.getServerId();
    }

}
