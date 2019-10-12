import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.getMessage().getContentRaw().trim().toLowerCase().equals("hello")) {
            event.getChannel().sendMessage("Hello, " + event.getAuthor().getAsMention()).queue();
        }
    }
}
