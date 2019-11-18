package command;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public abstract class CommandAbstract {
    public void deleteMessage(Message m) {
        m.delete().queueAfter(3, TimeUnit.SECONDS);
    }
}

