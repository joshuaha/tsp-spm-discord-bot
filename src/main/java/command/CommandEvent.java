package command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandEvent implements Command {
    CommandEvent() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAlias() {
        return "event";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String[] args, MessageReceivedEvent event) {

    }
}
