package command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.FileNotFoundException;
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
        try {
            final URL resource = this.getClass().getClassLoader().getResource("HelpOutput.txt");
            final File file = resource == null ? new File("") : new File(resource.toURI());
            final Scanner in = new Scanner(file);
            final StringBuilder message = new StringBuilder();
            while (in.hasNextLine()) {
                message.append(in.nextLine()).append(System.lineSeparator());
            }
            in.close();
            return message.toString();
        } catch (FileNotFoundException | URISyntaxException ex) {
            ex.printStackTrace();
            return "Help information missing!";
        }
    }

    /**
     * Retrieves the message displayed when the !help poll or !help event commands are called.
     *
     * @param command specifies whether to print help for the event commands or the poll commands
     * @param action the specific sub
     */
    private String getSpecificHelpMessage(String command, String action) {
        try {
            // If command is !help poll. //
            if (command.equalsIgnoreCase("poll")) {
                final URL resource;
                // If command is !help poll create. //
                if (action.equalsIgnoreCase("create")) {
                    resource = this.getClass().getClassLoader().getResource("PollCreateHelpOutput.txt");
                // If command is !help poll edit. //
                } else if (action.equalsIgnoreCase("edit")) {
                    resource = this.getClass().getClassLoader().getResource("PollEditHelpOutput.txt");
                // If command is !help poll vote. //
                } else if (action.equalsIgnoreCase("vote")) {
                    resource = this.getClass().getClassLoader().getResource("PollVoteHelpOutput.txt");
                // If command is !help poll all. Essentially a specific  way of calling all commands. //
                } else if (action.equalsIgnoreCase("all") || action.equalsIgnoreCase("")) {
                    resource = this.getClass().getClassLoader().getResource("PollHelpOutput.txt");
                } else {
                    // If the !help poll ____ matches none of the above ways. //
                    resource = this.getClass().getClassLoader().getResource("PollHelpOutput.txt");
                }
                // Grabs the contents of the file specified above and displays it back to Discord. //
                final File file = resource == null ? new File("") : new File(resource.toURI());
                final Scanner in = new Scanner(file);
                final StringBuilder message = new StringBuilder();
                while (in.hasNextLine()) {
                    message.append(in.nextLine()).append(System.lineSeparator());
                }
                in.close();
                return message.toString();
            // If command is !help event. //
            } else if (command.equalsIgnoreCase("event")) {
                // Grabs the contents of the file specified above and displays it back to Discord. //
                final URL resource = this.getClass().getClassLoader().getResource("EventHelpOutput.txt");
                final File file = resource == null ? new File("") : new File(resource.toURI());
                final Scanner in = new Scanner(file);
                final StringBuilder message = new StringBuilder();
                while (in.hasNextLine()) {
                    message.append(in.nextLine()).append(System.lineSeparator());
                }
                in.close();
                return message.toString();
            } else {
                return "Invalid Help Command";
            }
        // If help file is missing. //
        } catch (FileNotFoundException | URISyntaxException ex) {
            ex.printStackTrace();
            return "Help information missing!";
        }
    }
}
