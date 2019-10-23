package command;

import factory.ServiceFactory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.joda.time.LocalDateTime;
import poll.DiscordPoll;
import poll.DiscordPollDao;
import poll.DiscordPollOld;

import java.util.Arrays;

public class CommandPoll implements Command {
    private final DiscordPollDao pollDao = ServiceFactory.getDiscordPollDao();

    CommandPoll() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAlias() {
        return "poll";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        if ("create".equals(args[0])) {
            final String pollName = args[1];
            final long owner = event.getAuthor().getIdLong();
            final String[] options = Arrays.copyOfRange(args, 2, args.length);
            this.create(pollName, owner, options);
        } else if ("vote".equals(args[0])) {
            final String pollName = args[1];
            final int vote = Integer.parseInt(args[2]) - 1;
            final long user = event.getAuthor().getIdLong();
            this.vote(pollName, user, vote, event);
        } else if ("results".equals(args[0])) {
            final String pollName = args[1];
            this.results(pollName, event);
        }
    }

    private void create(String name, long owner, String[] options) {
        final DiscordPoll poll = new DiscordPoll();
        poll.setId(DiscordPoll.getUniqueId());
        poll.setOwner(owner);
        poll.setOpenTime(LocalDateTime.now());
        poll.setCloseTime(LocalDateTime.now().plusDays(1));
    }

    private void vote(String pollName, long user, int vote, MessageReceivedEvent event) {
        this.pollDao.setVote(pollName, user, vote);
    }

    private void results(String pollName, MessageReceivedEvent event) {
        final DiscordPollOld poll = null;
        final StringBuilder message = new StringBuilder();
        message.append(poll.getName()).append(System.lineSeparator());
        for (DiscordPollOld.Option option : poll.getOptions()) {
            message.append(option.getText())
                    .append(": ")
                    .append(option.getVotes())
                    .append(System.lineSeparator());
        }
        //event.getMessage().delete().queue();
        event.getMessage().getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(message).queue());
    }
}
