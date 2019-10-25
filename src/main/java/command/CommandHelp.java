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
     * Retrieves the message displayed then the Poll Help or Event Help commands are called.
     * @param command specifies whether to print help for the event commands or the poll commands
     */
    public String specificHelp(String command) {
        try {
            if (command.equals("poll")) {
                final URL resource = this.getClass().getClassLoader().getResource("PollHelpOutput.txt");
                final File file = resource == null ? new File("") : new File(resource.toURI());
                final Scanner in = new Scanner(file);
                final StringBuilder message = new StringBuilder();
                while (in.hasNextLine()) {
                    message.append(in.nextLine()).append(System.lineSeparator());
                }
                in.close();
                return message.toString();
            } else if (command.equals("event")) {
                final URL resource = this.getClass().getClassLoader().getResource("EventOutputHelp.txt");
                final File file = resource == null ? new File("") : new File(resource.toURI());
                final Scanner in = new Scanner(file);
                final StringBuilder message = new StringBuilder();
                while (in.hasNextLine()) {
                    message.append(in.nextLine()).append(System.lineSeparator());
                }
                in.close();
                return message.toString();
            }
            else {
                return "Invalid Help Command";
            }
        } catch (FileNotFoundException| URISyntaxException ex) {
            ex.printStackTrace();
            return "Help information missing!";
        }
    }
}
