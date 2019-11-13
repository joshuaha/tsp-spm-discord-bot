package command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import test.TestingBot;

public class CommandTest implements Command {
    private TestingBot tb;

    CommandTest() {
        tb = new TestingBot();
    }

    @Override
    public String getAlias() {
        return "test";
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        tb.initializeTestingPoll( event );

        if ( args[0].equalsIgnoreCase("general") ) {
            tb.sendTestStrings( event );
        } else if ( args[0].equalsIgnoreCase("edit") ) {
            tb.sendEditTest( event );
        } else if ( args[0].equalsIgnoreCase( "create" ) ) {
            tb.sendCreateTest( event );
        } else if ( args[0].equalsIgnoreCase( "help" ) ) {
            tb.sendHelpTest( event );
        } else {
            tb.sendTestStrings( event );
        }

        //Ignoring check output more work to automate than to manually check output.
//        if ( tb.checkOutput( event ) ) System.out.println( "Test passed!" );
//        else System.out.println( "Test failed.");
    }
}
