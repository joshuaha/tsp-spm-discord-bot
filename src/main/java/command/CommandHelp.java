package command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class CommandHelp implements Command {
    /**
     * {@inheritDoc}
     */
    @Override
    public String getAlias() {
        return "help";
    }

    /**
     * {@inheritDoc}
     * Retrieves the message displayed when a specific !help ____ command is called, from either getHelpMessage or getSpecificHelp method depending on usage.
     */
    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        final String message;
        if (args.length > 0) {
            if (CommandRegistry.COMMON_COMMANDS.getCommand(args[0]) != null) {
                if ("poll".equalsIgnoreCase(args[0]) && args.length > 1) {
                    if (CommandRegistry.POLL_COMMANDS.getCommand(args[1]) != null) {
                        message = this.getSpecificHelpMessage(args[0], args[1]);
                    } else {
                        message = "Poll subcommand " + args[1] + " not found";
                    }
                } else {
                    message = this.getSpecificHelpMessage(args[0], "");
                }
            } else {
                message = "Command " + args[0] + " not found";
            }
        } else {
            message = this.getHelpMessage();
        }
        event.getChannel().sendMessage(message).queue();
    }

    /**
     * Retrieves the message displayed when the !help command is called.
     */
    private String getHelpMessage() {
        final InputStream stream = this.getClass().getClassLoader().getResourceAsStream("HelpOutput.txt");
        if (stream != null) {
            final Scanner in = new Scanner(stream);
            final StringBuilder message = new StringBuilder();
            while (in.hasNextLine()) {
                message.append(in.nextLine()).append(System.lineSeparator());
            }
            in.close();
            return message.toString();
        } else {
            return "Help output not found.";
        }
    }

    /**
     * Retrieves the message displayed when the !help poll or !help event commands are called.
     *
     * @param command specifies whether to print help for the event commands or the poll commands
     * @param action  the specific sub
     */
    private String getSpecificHelpMessage(String command, String action) {
        // If command is !help poll. //
        if (command.equalsIgnoreCase("poll")) {
            final InputStream stream;
            // If command is !help poll create. //
            if (action.equalsIgnoreCase("create")) {
                stream = this.getClass().getClassLoader().getResourceAsStream("PollCreateHelpOutput.txt");
                // If command is !help poll edit. //
            } else if (action.equalsIgnoreCase("edit")) {
                stream = this.getClass().getClassLoader().getResourceAsStream("PollEditHelpOutput.txt");
                // If command is !help poll vote. //
            } else if (action.equalsIgnoreCase("vote")) {
                stream = this.getClass().getClassLoader().getResourceAsStream("PollVoteHelpOutput.txt");
                // If command is !help poll all. Essentially a specific  way of calling all commands. //
            } else if (action.equalsIgnoreCase("all") || action.equalsIgnoreCase("")) {
                stream = this.getClass().getClassLoader().getResourceAsStream("PollHelpOutput.txt");
            } else {
                // If the !help poll ____ matches none of the above ways. //
                stream = this.getClass().getClassLoader().getResourceAsStream("PollHelpOutput.txt");
            }
            if (stream != null) {
                // Grabs the contents of the file specified above and displays it back to Discord. //
                final Scanner in = new Scanner(stream);
                final StringBuilder message = new StringBuilder();
                while (in.hasNextLine()) {
                    message.append(in.nextLine()).append(System.lineSeparator());
                }
                in.close();
                return message.toString();
            } else {
                return "Help output not found.";
            }
            // If command is !help event. //
        } else if (command.equalsIgnoreCase("event")) {
            // Grabs the contents of the file specified above and displays it back to Discord. //
            final InputStream stream = this.getClass().getClassLoader().getResourceAsStream("EventHelpOutput.txt");
            if (stream != null) {
                final Scanner in = new Scanner(stream);
                final StringBuilder message = new StringBuilder();
                while (in.hasNextLine()) {
                    message.append(in.nextLine()).append(System.lineSeparator());
                }
                in.close();
                return message.toString();
            } else {
                return "Help output not found.";
            }
        } else {
            return "Invalid Help Command";
        }
    }
}
