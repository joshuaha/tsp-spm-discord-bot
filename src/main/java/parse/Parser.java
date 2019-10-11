package parse;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class Parser {
    private char cmdMark = '!';

    public static void main(String[] args) {
        JDABuilder builder = new JDABuilder( AccountType.BOT );
        String token = ""; //TODO - Insert token.
        //TODO - Have gitignored text files with personal tokens in it.

        builder.setToken( token );
        try {
            builder.buildAsync();
        } catch (LoginException e) {
            System.out.println( "Invalid login." );
            e.printStackTrace();
        }
    }

    public void parseCommand( String command ) {
        //!poll create <pollname> <ans1>, <and2>, ...
        //!poll edit <pollname> <newname> <newAns1> <newAns2> ... -- More commands = add poll option
        //!poll answer <pollname> <ans>
        //!event create <eventname> <location> <dateTime>
        //!event edit <eventname> <newname> <newLocation> <newTime>
        //!event answer <eventname> <y/n>
        //!help
        //!help <commandname>

        //If not command marked ignore the text.
        if ( !command.startsWith("!") ) return;

        String[] cmdArgs = command.split( " ");

        switch ( cmdArgs[0] ) {
            case "!poll":
                if ( cmdArgs.length < 4 ) {
                    System.out.println( "Invalid number of arguments" );
                    break;
                }

                String pollName = cmdArgs[2];

                switch ( cmdArgs[1] ) {
                    case "create":
                        if ( cmdArgs.length < 5 ) {
                            System.out.println( "Invalid number of arguments" ); //Poll must have at least two options.
                            break;
                        }
                        String[] answers = new String[ cmdArgs.length - 3];
                        for ( int i = 3; i < cmdArgs.length; i++ ) {
                            answers[i-3] = cmdArgs[i];
                        }
                        createPoll( pollName, answers );
                        break;

                    case "edit":
                        if ( cmdArgs.length < 6 ) {
                            System.out.println( "Invalid number of arguments" );
                            break;
                        }
                        String newName = cmdArgs[3];
                        String[] newAnswers = new String[ cmdArgs.length - 4];
                        for ( int i = 4; i < cmdArgs.length; i++ ) {
                            newAnswers[i-4] = cmdArgs[i];
                        }
                        editPoll( pollName, newName, newAnswers );
                        break;

                    case "answer":
                        String response = cmdArgs[3];
                        answerPoll( pollName, response );
                        break;

                    default:
                        System.out.println( "Invalid poll command" );
                        break;
                }
                break;

            case "!event":
                if ( cmdArgs.length < 4 ) {
                    System.out.println( "Invalid number of arguments" );
                    break;
                }

                String eventName = cmdArgs[2];

                switch ( cmdArgs[1] ) {
                    case "create":
                        if ( cmdArgs.length < 5 ) {
                            System.out.println( "Invalid number of arguments" );
                            break;
                        }
                        String location = cmdArgs[3];
                        String time = cmdArgs[4];
                        createEvent( eventName, location, time );
                        break;

                    case "edit":
                        if ( cmdArgs.length < 6 ) {
                            System.out.println( "Invalid number of arguments" );
                            break;
                        }
                        String newEventName = cmdArgs[3];
                        String newLocation = cmdArgs[4];
                        String newTime = cmdArgs[5];
                        editEvent( eventName, newEventName, newLocation, newTime);
                        break;

                    case "answer":
                        boolean isGoing;
                        if ( cmdArgs[3].equalsIgnoreCase( "y" ) ) {
                            isGoing = true;
                        } else if ( cmdArgs[3].equalsIgnoreCase( "n" ) ) {
                            isGoing = false;
                        } else {
                            System.out.println( "Invalid argument.");
                            break;
                        }
                        answerEvent( eventName, isGoing );
                        break;

                    default:
                        System.out.println( "Invalid event command" );
                        break;
                }
                break;

            case "!help":
                if ( cmdArgs.length == 1 ) {
                    help( null );
                } else {
                    String commandName = cmdArgs[1];
                    help( commandName );
                }
                break;

            default:
                System.out.println( "Invalid Command" );
                break;
       }
    }

    public void createPoll ( String pollName, String[] responses ) {
    }

    public void editPoll ( String pollName, String newName, String[] newResponses ) {
    }

    public void answerPoll ( String pollName, String response ) {
    }

    public void createEvent ( String eventName, String location, String time ) {
    }

    public void editEvent ( String eventName, String newName, String newLocation, String newTime ) {
    }

    public void answerEvent ( String eventName, boolean going ) {
    }

    public void help ( String command ) {
        //Leave string null for default help command.
    }
}