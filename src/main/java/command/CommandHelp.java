package command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class CommandHelp implements Command {
    CommandHelp() {

    }

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
        event.getChannel().sendMessage(this.getHelpMessage()).queue();
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
     * Sends the message for the !help event or !help poll command to the channel that it was called in
     *
     * @param command specifies whether to print help for the event commands or the poll commands
     * @param event   the event representing the help command being called
     */
    public void specificHelp(String command, MessageReceivedEvent event) {
        try {
            if (command.equals("poll")) {
                File pollOutput = new File("PollHelpOutput.txt");
                Scanner scan = new Scanner(pollOutput);

                while (scan.hasNextLine()) {
                    event.getChannel().sendMessage(scan.nextLine()).queue();
                }
            } else if (command.equals("event")) {
                File eventOutput = new File("EventHelpOutput.txt");
                Scanner scan = new Scanner(eventOutput);

                while (scan.hasNextLine()) {
                    event.getChannel().sendMessage(scan.nextLine()).queue();
                }
            }
        } catch (FileNotFoundException ex) {
            event.getChannel().sendMessage("Help information missing!").queue();
            ex.printStackTrace();
        }
    }
}
