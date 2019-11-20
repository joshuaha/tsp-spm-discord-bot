package command;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class SendDeleteMessage {
    public static void deleteMessage(Message m) {
        m.delete().queueAfter(30, TimeUnit.SECONDS);
    }

    public static Message sendMessage(MessageReceivedEvent event, String message) {
        return event.getChannel().sendMessage(message).complete();
    }
}

