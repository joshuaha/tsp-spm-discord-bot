package command;

import bot.DiscordPoll;
import bot.TempData;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;

public class CommandPoll implements Command {
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
        TempData.polls.put(poll.getName(), poll);
    }

    private void vote(String pollName, long user, int vote, MessageReceivedEvent event) {
        final DiscordPoll poll = TempData.polls.get(pollName);
        poll.setVote(user, vote);
    }

    private void results(String pollName, MessageReceivedEvent event) {
        final DiscordPoll poll = TempData.polls.get(pollName);
        final StringBuilder message = new StringBuilder();
        message.append(poll.getName()).append(System.lineSeparator());
        for (int i = 0; i < poll.getOptions().size(); i++) {
            message.append(poll.getOptions().get(i))
                    .append(": ")
                    .append(poll.getResults().get(i))
                    .append(System.lineSeparator());
        }
        //event.getMessage().delete().queue();
        event.getMessage().getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(message).queue());
    }
}
