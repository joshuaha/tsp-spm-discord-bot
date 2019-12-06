package command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;


public class CommandPoll implements Command {
    /**
     * Returns the name of the command specified by a Discord user.
     * {@inheritDoc}
     */
    @Override
    public String getAlias() {
        return "poll";
    }

    /**
     * Executes the command by referencing CommandRegistry with a set of arguments.
     * Deletes the message if command is null.
     * @param args list of command arguments
     * @param event the message event
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
            SendDeleteMessage.sendDeleteMessage(event, "Invalid command. Type \"!help\" for help.");
        }
    }
}