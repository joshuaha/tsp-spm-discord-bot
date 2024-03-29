package command;

import factory.ServiceFactory;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.joda.time.LocalDateTime;
import poll.DiscordPoll;
import poll.DiscordPollDao;
import poll.DiscordPollFormatter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandPollCreate implements Command {
    private final DiscordPollDao pollDao = ServiceFactory.getDiscordPollDao();

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAlias() {
        return "create";
    }

    /**
     * {@inheritDoc}
     * Generates a poll given a user's input if possible, sending a message in Discord stating that the poll was or was able to be created.
     */
    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        final boolean success;
        if (args.length >= 3) {
            final Message message = event.getChannel().sendMessage("Creating poll...").complete();
            final DiscordPoll poll = new DiscordPoll();
            final List<String> options = Arrays.asList(Arrays.copyOfRange(args, 1, args.length));
            final List<Integer> votes = Collections.nCopies(options.size(), 0);
            // Sets the poll's data to the user's specifications. //
            // See DiscordPoll.java for reference to the methods such as setId. //
            poll.setId(DiscordPoll.getUniqueId());
            poll.setText(args[0]);
            poll.setOwnerId(event.getAuthor().getIdLong());
            poll.setOpenTime(LocalDateTime.now());
            poll.setCloseTime(LocalDateTime.now().plusDays(1));
            poll.setServerId(event.getGuild().getIdLong());
            poll.setChannelId(event.getChannel().getIdLong());
            poll.setMessageId(message.getIdLong());
            success = this.pollDao.createPoll(poll) && this.pollDao.setOptions(poll.getId(), options);
            final String display = DiscordPollFormatter.getDisplayMessage(poll, options, votes);
            event.getChannel().editMessageById(poll.getMessageId(), display).queue();
        } else {
            success = false;
        }
        if (!success) {
            Message m = SendDeleteMessage.sendMessage(event, "Unable to create poll. Be sure to specify the correct number of arguments. Type \"!help poll create\" for help.");
            SendDeleteMessage.deleteMessage(m);
        }
    }
}
