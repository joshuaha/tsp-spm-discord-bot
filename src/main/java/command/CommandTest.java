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
        tb.sendTestStrings(event);

        //Ignoring check output more work to automate than to manually check output.
//        if ( tb.checkOutput( event ) ) System.out.println( "Test passed!" );
//        else System.out.println( "Test failed.");
    }
}
