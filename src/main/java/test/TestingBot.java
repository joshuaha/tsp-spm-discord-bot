package test;

import factory.ServiceFactory;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import poll.DiscordPoll;
import poll.DiscordPollDao;
import poll.DiscordPollDaoSql;
import poll.DiscordPollFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestingBot {
    private final DiscordPollDao pollDao = ServiceFactory.getDiscordPollDao();

    /**
     * Create a poll to use for automated tests
     * @param event
     */
    public void initializeTestingPoll( MessageReceivedEvent event ) {

        final Message message = event.getChannel().sendMessage("Creating testing poll...").complete();
        final List<String> options = new ArrayList<>();
        options.add( "Option A" ); options.add( "Option B");
        final List<Integer> votes = Collections.nCopies(options.size(), 0);

        if ( this.pollDao.getPoll( "00000") == null ) {

            final DiscordPoll poll = new DiscordPoll();
            poll.setId( "00000" );
            poll.setText( "TESTING POLL");
            poll.setOwnerId( event.getJDA().getSelfUser().getIdLong() );
            poll.setServerId(event.getGuild().getIdLong());
            poll.setChannelId(event.getChannel().getIdLong());
            poll.setMessageId(message.getIdLong());
            boolean success = this.pollDao.createPoll(poll) && this.pollDao.setOptions( "00000", options);

            final String display = DiscordPollFormatter.getDisplayMessage(poll, options, votes);
            event.getChannel().editMessageById(poll.getMessageId(), display).queue();
            if (!success) {
                event.getChannel().sendMessage("Unable to create poll. Type \"!help\" for help.").queue();
            }

        } else {

            final DiscordPoll poll = this.pollDao.getPoll( "00000");
            poll.setText( "TESTING POLL");
            poll.setOwnerId( event.getJDA().getSelfUser().getIdLong() );
            poll.setServerId(event.getGuild().getIdLong());
            poll.setChannelId(event.getChannel().getIdLong());
            poll.setMessageId(message.getIdLong());
            boolean success = this.pollDao.setOptions( "00000", options) && this.pollDao.updatePoll( poll );

            final String display = DiscordPollFormatter.getDisplayMessage(poll, options, votes);
            event.getChannel().editMessageById(poll.getMessageId(), display).queue();
            if (!success) {
                event.getChannel().sendMessage("Unable to update poll. Type \"!help\" for help.").queue();
            }
        }

    }

    /**
     * Sends messages to the command line to be tested.
     * @param event
     */
    public void sendTestStrings( MessageReceivedEvent event ) {
        final StringBuilder message = new StringBuilder();
//        message.append( "!poll create \"NoArgs\" \n" );
//        message.append( "!poll create \"\" \"\" \n" );
//        message.append( "!poll create NoQuotes Ans1 \n" );
//        message.append( "!poll create \n" );
//        message.append( "!poll \n" );
//        message.append( "!poll create \"PassPoll\" \"Ans1\" \"Ans2\" \n" );
        message.append( "This test is no longer active. Call specific test." );

        event.getChannel().sendMessage( message ).queue();
    }

    public void sendCreateTest( MessageReceivedEvent event ) {

        final StringBuilder message = new StringBuilder();
        message.append( "!poll create \"NoArgs\" \n" );
        message.append( "!poll create \"\" \"\" \n" );
        message.append( "!poll create NoQuotes Ans1 \n" );
        message.append( "!poll create \n" );
        message.append( "!poll \n" );
        message.append( "!poll create \"PassPoll\" \"Ans1\" \"Ans2\" \n" );

        event.getChannel().sendMessage( message ).queue();
    }

    public void sendHelpTest( MessageReceivedEvent event ) {

        final StringBuilder message = new StringBuilder();
        message.append( "!help \"\" \n" );
        message.append( "!help notpoll \n" );
        message.append( "!help \n" );
        message.append( "!help poll \n" );
        message.append( "!help event \n" );
        message.append( "!help help \n" );
        event.getChannel().sendMessage( message ).queue();
    }

    public void sendEditTest( MessageReceivedEvent event ) {

        final StringBuilder message = new StringBuilder();
        message.append( "!poll edit 00000 text uwu \n" ); //Change the text for the poll. PASS

        message.append( "!poll edit 00000 OpeNtIMe \"06-09-2012\" \n" ); //Test open time change. FAIL
        message.append( "!poll edit 00000 OPENTIME \"12:00pm\" \n" ); //Test open time change. FAIL
        message.append( "!poll edit 00000 openTIME \"null\" \n" ); //Test open time change. FAIL
        message.append( "!poll edit 00000 opentime \"11-11-2019 04:20pm\" \n" ); //Test open time change. PASS

        message.append( "!poll edit 00000 closeTIME \"06-09-2019 04:20am\" \n" ); //Test close time before open. PASS
        message.append( "!poll edit 00000 closetime \"06-09-2012\" \n" ); //Test open time change. FAIL
        message.append( "!poll edit 00000 CLOSETIME \"12:00pm\" \n" ); //Test open time change. FAIL
        message.append( "!poll edit 00000 cLoSeTiMe \"null\" \n" ); //Test open time change. FAIL
        message.append( "!poll edit 00000 CLOSEtime \"06-09-2020 04:20am\" \n" ); //Test open time change. PASS

        message.append( "!poll edit 00000 option 1 \"New First\" \n" ); //Edit first option. Else option will be "Option A".
        message.append( "!poll vote 00000 1 \n" ); //This vote should be reset.
        message.append( "!poll edit 00000 option 2 \"New Second\" \n" ); //Edit second option. Else option will be "Option B".
        message.append( "!poll edit 00000 option add \"Third Option\" \n" ); //Add third option.
        message.append( "!poll edit 00000 option 4 \"This should fail\" \n" ); //If this options appears. You did it wrong.
        message.append( "!poll edit 00000 text uwu \n" ); //Change the text for the poll.
        message.append( "!poll edit 00000 text \"\" \n" ); //Change the text for the poll.


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

