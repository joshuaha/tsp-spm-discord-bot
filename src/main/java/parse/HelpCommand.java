package parse;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HelpCommand extends ListenerAdapter{

    /**
     * Sends the message for the !help command to the channel that it was called in
     * @param event the event representing the help command being called
     * @throws FileNotFoundException if the text file where the help output is stored is not found
     */
    public void help(MessageReceivedEvent event) throws FileNotFoundException {

        File output = new File("HelpOutput.txt");
        Scanner scan = new Scanner(output);

        while(scan.hasNextLine()) {
            event.getChannel().sendMessage(scan.nextLine()).queue();
        }

    }

    /**
     * Sends the message for the !help event or !help poll command to the channel that it was called in
     * @param command specifies whether to print help for the event commands or the poll commands
     * @param event the event representing the help command being called
     * @throws FileNotFoundException if the text file where the help output is stored is not found
     */
    public void specificHelp(String command, MessageReceivedEvent event) throws FileNotFoundException {
        if (command.equals("poll")) {
            File pollOutput = new File("PollHelpOutput.txt");
            Scanner scan = new Scanner(pollOutput);

            while(scan.hasNextLine()) {
                event.getChannel().sendMessage(scan.nextLine()).queue();
            }
        }
        else if(command.equals("event")) {
            File eventOutput = new File("EventHelpOutput.txt");
            Scanner scan = new Scanner(eventOutput);

            while(scan.hasNextLine()) {
                event.getChannel().sendMessage(scan.nextLine()).queue();
            }
        }

    }

}
