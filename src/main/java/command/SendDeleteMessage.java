package command;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.util.concurrent.TimeUnit;

// Deletes a users message after a short delay. //
public class SendDeleteMessage {
    /**
     * Deletes a message after a five second delay
     * @param m The message to be deleted.
     */
    public static void deleteMessage(Message m) {
        m.delete().queueAfter(30, TimeUnit.SECONDS);
    } //30

    /**
     *
     * @param event When a message is sent.
     * @param message The message to be sent.
     * @return Returns the message in the appropriate channel.
     */
    public static Message sendMessage(MessageReceivedEvent event, String message) {
        return event.getChannel().sendMessage(message).complete();
    }

    /**
     * @param event When a message is sent.
     * @param message The message to be deleted.
     */
    public static void sendDeleteMessage(MessageReceivedEvent event, String message) {
        deleteMessage(sendMessage(event, message));
    }
}

