package command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

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
     */
    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        if (args.length > 0 && (args[0].equals("poll") || args[0].equals("event"))) {
            if (args.length > 1) {
                if ((args[1].equalsIgnoreCase("create")) || (args[1].equalsIgnoreCase("edit")) || (args[1].equalsIgnoreCase("vote")) || (args[1].equalsIgnoreCase("all"))) {
                    event.getChannel().sendMessage(this.specificHelp(args[0], args[1])).queue();
                } else {
                    event.getChannel().sendMessage(this.getHelpMessage()).queue();
                }
            } else {
                event.getChannel().sendMessage(this.specificHelp(args[0], "")).queue();
            }
        } else {
            event.getChannel().sendMessage(this.getHelpMessage()).queue();
        }
    }

    /**
     * Retrieves the message displayed when the Help command is called.
     */
    public String getHelpMessage() {
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
     * Retrieves the message displayed then the Poll Help or Event Help commands are called.
     *
     * @param command specifies whether to print help for the event commands or the poll commands
     */
    public String specificHelp(String command, String action) {
        try {
            if (command.equalsIgnoreCase("poll")) {
                final URL resource;
                if (action.equalsIgnoreCase("create")) {
                    resource = this.getClass().getClassLoader().getResource("PollCreateHelpOutput.txt");
                } else if (action.equalsIgnoreCase("edit")) {
                    resource = this.getClass().getClassLoader().getResource("PollEditHelpOutput.txt");
                } else if (action.equalsIgnoreCase("vote")) {
                    resource = this.getClass().getClassLoader().getResource("PollVoteHelpOutput.txt");
                } else if (action.equalsIgnoreCase("all") || action.equalsIgnoreCase("")) {
                    resource = this.getClass().getClassLoader().getResource("PollHelpOutput.txt");
                } else {
                    System.out.println("TEST");
                    resource = this.getClass().getClassLoader().getResource("PollHelpOutput.txt");
                }
                final File file = resource == null ? new File("") : new File(resource.toURI());
                final Scanner in = new Scanner(file);
                final StringBuilder message = new StringBuilder();
                while (in.hasNextLine()) {
                    message.append(in.nextLine()).append(System.lineSeparator());
                }
                in.close();
                return message.toString();
            } else if (command.equalsIgnoreCase("event")) {
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
        } catch (FileNotFoundException | URISyntaxException ex) {
            ex.printStackTrace();
            return "Help information missing!";
        }
    }
}
