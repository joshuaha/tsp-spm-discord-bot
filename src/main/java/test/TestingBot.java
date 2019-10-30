package test;

import factory.ServiceFactory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import poll.DiscordPollDao;

public class TestingBot {
    private final DiscordPollDao pollDao = ServiceFactory.getDiscordPollDao();

    public void sendTestStrings( MessageReceivedEvent event ) {
        final StringBuilder message = new StringBuilder();
        message.append( "!poll create \"NoArgs\" \n" );
        message.append( "!poll create \"\" \"\" \n" );
        message.append( "!poll create NoQuotes Ans1 \n" );
        message.append( "!poll create \n" );
        message.append( "!poll \n" );
        message.append( "!poll create \"PassPoll\" \"Ans1\" \"Ans2\" \n" );
        message.append( "!help \"\" \n" );
        message.append( "!help notpoll \n" );
        message.append( "!help \n" );
        message.append( "!help poll \n" );
        message.append( "!help event \n" );
        message.append( "!help help \n" );


        event.getChannel().sendMessage( message ).queue();
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

