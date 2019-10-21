package command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {
    /**
     * Gets the alias which is used to call this command.
     * i.e. !alias arg0 arg1 ... argn
     *
     * @return The command alias
     */
    String getAlias();

    /**
     * Executes the command action when the command is called.
     *
     * @param args list of command arguments
     * @param event the message event
     */
    void execute(String[] args, MessageReceivedEvent event);
}
