package command;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class SendDeleteMessage {
    public static void deleteMessage(Message m) {
        m.delete().queueAfter(5, TimeUnit.SECONDS);
    } //30

    public static Message sendMessage(MessageReceivedEvent event, String message) {
        return event.getChannel().sendMessage(message).complete();
    }

    public static void sendDeleteMessage(MessageReceivedEvent event, String message) {
        deleteMessage(sendMessage(event, message));
    }
}

