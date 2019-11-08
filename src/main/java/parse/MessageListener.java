package parse;

import command.Command;
import command.Commands;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageListener extends ListenerAdapter {
    private static final String IDENTIFIER = "!";

    /**
     * Parses any message received in any Discord channel on any server in which the bot is active.
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        final Scanner in = new Scanner(event.getMessage().getContentRaw());
        while(in.hasNextLine()) {
            final String line = in.nextLine();
            if (line.startsWith(IDENTIFIER)) {
                final String input = line.substring(IDENTIFIER.length());
                final String[] split = input.split("\\s+", 2);
                final String alias = split.length >= 1 ? split[0] : null;
                final String[] args = this.splitArguments(split.length == 2 ? split[1] : null);
                final Command command = Commands.getInstance().getCommand(alias);
                if (command != null) {
                    command.execute(args, event);
                    event.getMessage().delete().queue();
                } else {
                    event.getChannel().sendMessage(String.format("Command \"%s\" not recognized. " +
                            "Type \"!help\" for help.", alias)).queue();
                }
            }
        }
    }

    /**
     * Splits an input string received from the user into an array of arguments, respecting
     * whitespace and quotations.
     *
     * @param args The string of arguments to parse
     * @return A split array of arguments contained within the input string
     */
    private String[] splitArguments(String args) {
        if (args == null) {
            return new String[]{};
        } else {
            final List<String> split = new ArrayList<>();
            //Separates by space but leaves strings surrounded by quotes as its own string.
            final Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(args);
            while (m.find()) {
                //Add .replace("\"", "") to remove surrounding quotes.
                split.add(m.group(1).replace("\"", ""));
            }
            return split.toArray(new String[]{});
        }
    }
}
