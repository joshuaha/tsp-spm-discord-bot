package command;

import factory.ServiceFactory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import poll.DiscordPollDao;

import java.util.Arrays;


public class CommandPoll implements Command {
    private final DiscordPollDao pollDao = ServiceFactory.getDiscordPollDao();

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
        final String alias = args.length == 0 ? null : args[0];
        final Command command = CommandRegistry.POLL_COMMANDS.getCommand(alias);
        if (command != null) {
            final String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
            command.execute(newArgs, event);
        } else {
            event.getChannel().sendMessage("Invalid command. Type \"!help\" for help.").queue();
        }
    }
}