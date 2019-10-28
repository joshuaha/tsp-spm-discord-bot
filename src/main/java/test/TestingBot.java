package test;

import factory.ServiceFactory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import poll.DiscordPollDao;

public class TestingBot {
    private final DiscordPollDao pollDao = ServiceFactory.getDiscordPollDao();

    public void sendTestStrings( MessageReceivedEvent event ) {
        final StringBuilder message = new StringBuilder();
        //TODO - Sawyer put in your commands
        message.append( "Command 1 \n" );
        message.append( "Command 2 \n" );
        message.append( "Command 3 \n" );
        message.append( "Command 4 \n" );
        message.append( "Command 5 \n" );

        event.getChannel().sendMessage( message );
    }

    /**
     * @return whether the test passed or failed.
     */
    public boolean checkOutput( MessageReceivedEvent event ) {
        boolean testPass = false;

        String expected = "";

        if ( event.getMessage().equals( expected ) ) testPass = true;

        return testPass;
    }
}

