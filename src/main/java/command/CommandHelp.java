package command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class CommandHelp extends ListenerAdapter {

    /**
     * Sends the message for the !help command to the channel that it was called in
     *
     * @param event the event representing the help command being called
     */
    public void help(MessageReceivedEvent event) {
        try {
            final URL resource = this.getClass().getClassLoader().getResource("HelpOutput.txt");
            if (resource != null) {
                final File file = new File(resource.toURI());
                final Scanner in = new Scanner(file);
                final StringBuilder message = new StringBuilder();
                while (in.hasNextLine()) {
                    message.append(in.nextLine()).append(System.lineSeparator());
                }
                in.close();
                event.getChannel().sendMessage(message.toString()).queue();
            }
        } catch (FileNotFoundException | URISyntaxException ex) {
            event.getChannel().sendMessage("Help information missing!").queue();
            ex.printStackTrace();
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
