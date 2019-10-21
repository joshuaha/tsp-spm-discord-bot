package command;

import factory.DaoFactory;
import poll.DiscordPoll;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import poll.DiscordPollDao;

import java.util.Arrays;

public class CommandPoll implements Command {
    private final DiscordPollDao pollDao  = DaoFactory.getDiscordPollDao();

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
            final String[] options = Arrays.copyOfRange(args, 2, args.length);
            this.create(pollName, options);
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

    private void create(String name, String[] options) {
        final DiscordPoll poll = new DiscordPoll();
        poll.setName(name);
        poll.setOptions(Arrays.asList(options));
        this.pollDao.createPoll(poll);
    }

    private void vote(String pollName, long user, int vote, MessageReceivedEvent event) {
        this.pollDao.setVote(pollName, user, vote);
    }

    private void results(String pollName, MessageReceivedEvent event) {
        final DiscordPoll poll = this.pollDao.getPoll(pollName);
        final StringBuilder message = new StringBuilder();
        message.append(poll.getName()).append(System.lineSeparator());
        for (DiscordPoll.Option option : poll.getOptions()) {
            message.append(option.getText())
                    .append(": ")
                    .append(option.getVotes())
                    .append(System.lineSeparator());
        }
        //event.getMessage().delete().queue();
        event.getMessage().getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(message).queue());
    }
}
