package parse;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import parse.Parser;

import java.util.Arrays;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            //TODO - Check that this is the correct way to get the message string.
            new Parser().parse(event.getMessage().getContentRaw(), event.getAuthor().getIdLong(), event);
        }
    }
}
