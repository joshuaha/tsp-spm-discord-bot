package command;

import java.util.HashMap;
import java.util.Map;

public class Commands {
    private static final Commands INSTANCE = new Commands();
    private final Map<String, Command> commands = new HashMap<>();

    private Commands() {
        this.registerCommands();
    }

    public static Commands getInstance() {
        return INSTANCE;
    }

    /**
     * Registers bot commands.
     */
    private void registerCommands() {
        this.registerCommand(new CommandHelp());
        this.registerCommand(new CommandPoll());
        this.registerCommand(new CommandEvent());
    }

    public void registerCommand(Command command) {
        final String alias = command.getAlias();
        if (this.commands.get(alias) == null) {
            this.commands.put(alias, command);
        } else {
            throw new IllegalArgumentException(String.format("Command with alias \"%s\" already registered!", alias));
        }
    }

    public Command getCommand(String alias) {
        return commands.get(alias);
    }
}
