package command;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    public static final CommandRegistry COMMON_COMMANDS = new CommandRegistry();
    public static final CommandRegistry POLL_COMMANDS = new CommandRegistry();
    private final Map<String, Command> commands = new HashMap<>();

    static {
        COMMON_COMMANDS.registerCommand(new CommandPoll());
        COMMON_COMMANDS.registerCommand(new CommandEvent());
        COMMON_COMMANDS.registerCommand(new CommandHelp());
        COMMON_COMMANDS.registerCommand(new CommandTest());

        POLL_COMMANDS.registerCommand(new CommandPollCreate());
        POLL_COMMANDS.registerCommand(new CommandPollVote());
        POLL_COMMANDS.registerCommand(new CommandPollEdit());
    }

    public void registerCommand(Command command) {
        final String alias = command.getAlias();
        if (this.commands.get(alias) == null) {
            this.commands.put(alias, command);
        } else {
            throw new IllegalArgumentException(String.format("Duplicate command with alias \"%s\"", alias));
        }
    }

    public Command getCommand(String alias) {
        return commands.get(alias);
    }
}
