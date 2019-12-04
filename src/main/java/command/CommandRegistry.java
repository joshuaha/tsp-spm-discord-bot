package command;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows the bot to recognise provided commands that have been registered in this file.
 */
public class CommandRegistry {
    public static final CommandRegistry COMMON_COMMANDS = new CommandRegistry();
    public static final CommandRegistry POLL_COMMANDS = new CommandRegistry();
    private final Map<String, Command> commands = new HashMap<>();

    static {
        // General / Unspecific commands. //
        COMMON_COMMANDS.registerCommand(new CommandPoll());
        COMMON_COMMANDS.registerCommand(new CommandEvent());
        COMMON_COMMANDS.registerCommand(new CommandHelp());
        COMMON_COMMANDS.registerCommand(new CommandTest());

        // Poll-specific commands. //
        POLL_COMMANDS.registerCommand(new CommandPollCreate());
        POLL_COMMANDS.registerCommand(new CommandPollVote());
        POLL_COMMANDS.registerCommand(new CommandPollEdit());
    }

    private void registerCommand(Command command) {
        if (command.getAlias() != null) {
            final String alias = command.getAlias().toLowerCase();
            if (this.commands.get(alias) == null) {
                this.commands.put(alias, command);
            } else {
                throw new IllegalArgumentException(String.format("Duplicate command with alias \"%s\"", alias));
            }
        } else {
            // If alias is null. //
            throw new IllegalArgumentException("Command alias cannot be null");
        }
    }

    public Command getCommand(String alias) {
        return commands.get(alias != null ? alias.toLowerCase() : null);
    }
}
