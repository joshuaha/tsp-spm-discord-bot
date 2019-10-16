package parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static void main(String[] args) {

        //Testing the parser
        Parser parser = new Parser();
        parser.parse( "!poll create name \"answer name\" ans2" );

    }

    public ArrayList<String> splitString ( String command ) {
        ArrayList<String> split = new ArrayList<>();

        //Separates by space but leaves strings surrounded by quotes as its own string.
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
        while ( m.find() ) {
            //Add .replace("\"", "") to remove surrounding quotes.
            split.add(m.group(1).replace("\"", "") );
        }
        return split;
    }

    public void parse( String command ) {
        //!poll create "pollname" "ans1", "and2", ...
        //!poll edit "pollname" "newname" "newAns1" "newAns2" ... -- More commands = add poll option
        //!poll answer "pollname" "ans"
        //!event create "eventname" "location" "time"
        //time format: mm/dd/yyyy xx:xxAM
        //!event edit "eventname" "newname" "newLocation" "newTime"
        //!event answer "eventname" <y/n>

        List<String> cmdArgs  = this.splitString(command);

        //Test that command is being split properly.
//        for ( String str : cmdArgs ) {
//            System.out.println( str );
//        }

        switch ( cmdArgs.get(0) ) {
            case "!poll":
                if ( cmdArgs.size() < 4 ) {
                    System.out.println( "Invalid number of arguments" );
                    break;
                }

                String pollName = cmdArgs.get(2);

                switch ( cmdArgs.get(1) ) {
                    case "create":
                        if ( cmdArgs.size() < 5 ) {
                            System.out.println( "Invalid number of arguments" ); //Poll must have at least two options.
                            break;
                        }
                        String[] answers = new String[ cmdArgs.size() - 3];
                        for ( int i = 3; i < cmdArgs.size(); i++ ) {
                            answers[i-3] = cmdArgs.get(i);
                        }
                        createPoll(pollName, answers);
                        break;

                    case "edit":
                        if ( cmdArgs.size() < 6 ) {
                            System.out.println( "Invalid number of arguments" );
                            break;
                        }
                        String newName = cmdArgs.get(3);
                        String[] newAnswers = new String[ cmdArgs.size() - 4];
                        for ( int i = 4; i < cmdArgs.size(); i++ ) {
                            newAnswers[i-4] = cmdArgs.get(i);
                        }
                        editPoll(pollName, newName, newAnswers);
                        break;

                    case "answer":
                        String response = cmdArgs.get(3);
                        answerPoll( pollName, response );
                        break;

                    default:
                        System.out.println("Invalid poll command");
                        break;
                }
                break;

            case "!event":
                if ( cmdArgs.size() < 4 ) {
                    System.out.println( "Invalid number of arguments" );
                    break;
                }

                String eventName = cmdArgs.get(2);

                switch ( cmdArgs.get(1) ) {
                    case "create":
                        if ( cmdArgs.size() < 5 ) {
                            System.out.println( "Invalid number of arguments" );
                            break;
                        }
                        String location = cmdArgs.get(3);
                        String time = cmdArgs.get(4);
                        createEvent( eventName, location, time );
                        break;

                    case "edit":
                        if ( cmdArgs.size() < 6 ) {
                            System.out.println( "Invalid number of arguments" );
                            break;
                        }
                        String newEventName = cmdArgs.get(3);
                        String newLocation = cmdArgs.get(4);
                        String newTime = cmdArgs.get(5);
                        editEvent( eventName, newEventName, newLocation, newTime);
                        break;

                    case "answer":
                        boolean isGoing;
                        if ( cmdArgs.get(3).equalsIgnoreCase( "y" ) ) {
                            isGoing = true;
                        } else if ( cmdArgs.get(3).equalsIgnoreCase( "n" ) ) {
                            isGoing = false;
                        } else {
                            System.out.println("Invalid argument.");
                            break;
                        }
                        answerEvent(eventName, isGoing);
                        break;

                    default:
                        System.out.println("Invalid event command");
                        break;
                }
                break;

            case "!help":
                if ( cmdArgs.size() == 1 ) {
                    help( );
                } else {
                    String commandName = cmdArgs.get(1);
                    help( commandName );
                }
                break;

            default:
                System.out.println("Invalid Command");
                break;
        }
    }

    /**
     * Splits a command containing arguments separated by whitespace and quotations and splits
     * it into an array of arguments.
     *
     * @param command The entire command received from the user
     * @return The command split into an array of arguments
     */
    protected  String[] splitArguments(String command) {
        final List<String> args = new ArrayList<>();
        //trim, replace any whitespace with a single space, then split on quotation marks
        final String[] splitByQuotes = command.trim().replaceAll("\\s+", " ").split("\"");
        for (int i = 0; i < splitByQuotes.length; i++) {
            //if string is not inside quotations, split on spaces
            //if it is inside quotations, do not split but add entire string
            if (i % 2 == 0)
                Collections.addAll(args, splitByQuotes[i].trim().split(" "));
            else
                Collections.addAll(args, splitByQuotes[i].trim());

        }
        //convert array list to array of arguments
        return args.toArray(new String[]{});
    }

    public void createPoll(String pollName, String[] responses) {
        System.out.println("Called create poll.");
    }

    public void editPoll(String pollName, String newName, String[] newResponses) {
        System.out.println("Called edit poll.");
    }

    public void answerPoll(String pollName, String response) {
        System.out.println("Called answer poll.");
    }

    public void createEvent(String eventName, String location, String time) {
        System.out.println("Called create event.");
    }

    public void editEvent(String eventName, String newName, String newLocation, String newTime) {
        System.out.println("Called edit event.");
    }

    public void answerEvent(String eventName, boolean going) {
        System.out.println("Called answer event.");
    }

    public void help(String command) {
        System.out.println("Called specific help.");
    }

    public void help() {
        //Default help
        System.out.println("Called default help.");
    }
}
