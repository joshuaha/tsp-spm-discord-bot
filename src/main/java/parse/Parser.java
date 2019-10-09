package parse;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

public class Parser {
    public static void main(String[] args) {
        //TODO - Create parsers for these commands : Poll - edit, answer. Event - create, answer. Help
        JDABuilder builder = new JDABuilder( AccountType.BOT );
        String token = "";
    }

    public void parseCommand( String command ) {
        //!poll <pollname> <ans1>, <and2>, ...
        //!editpoll <pollname> <newname> <newAns1> <newAns2> ... -- More commands = add poll option
        //!answerpoll <pollname> <ans>
        //!event <eventname> <location> <dateTime>
        //!editevent <eventname> <newname> <newLocation> <newTime>
        //!answerevent <eventname> <y/n>
        //!help
        //!help <commandname>
    }
}
