package parse;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Parser {
    private char cmdMark = '!';

    public void parse(MessageReceivedEvent event) {
        final String command = event.getMessage().getContentRaw().trim();

        //!poll create <pollname> <ans1>, <and2>, ...
        //!poll edit <pollname> <newname> <newAns1> <newAns2> ... -- More commands = add poll option
        //!poll answer <pollname> <ans>
        //!event create <eventname> <location> <dateTime>
        //!event edit <eventname> <newname> <newLocation> <newTime>
        //!event answer <eventname> <y/n>
        //!help
        //!help <commandname>

        //If not command marked ignore the text.
        if (!command.startsWith("!")) return;

        String[] cmdArgs = this.splitArguments(command);

        switch (cmdArgs[0]) {
            case "!poll":
                if (cmdArgs.length < 4) {
                    System.out.println("Invalid number of arguments");
                    break;
                }

                String pollName = cmdArgs[2];

                switch (cmdArgs[1]) {
                    case "create":
                        if (cmdArgs.length < 5) {
                            System.out.println("Invalid number of arguments"); //Poll must have at least two options.
                            break;
                        }
                        String[] answers = new String[cmdArgs.length - 3];
                        for (int i = 3; i < cmdArgs.length; i++) {
                            answers[i - 3] = cmdArgs[i];
                        }
                        createPoll(pollName, answers);
                        break;

                    case "edit":
                        if (cmdArgs.length < 6) {
                            System.out.println("Invalid number of arguments");
                            break;
                        }
                        String newName = cmdArgs[3];
                        String[] newAnswers = new String[cmdArgs.length - 4];
                        for (int i = 4; i < cmdArgs.length; i++) {
                            newAnswers[i - 4] = cmdArgs[i];
                        }
                        editPoll(pollName, newName, newAnswers);
                        break;

                    case "answer":
                        String response = cmdArgs[3];
                        answerPoll(pollName, response);
                        break;

                    default:
                        System.out.println("Invalid poll command");
                        break;
                }
                break;

            case "!event":
                if (cmdArgs.length < 4) {
                    System.out.println("Invalid number of arguments");
                    break;
                }

                String eventName = cmdArgs[2];

                switch (cmdArgs[1]) {
                    case "create":
                        if (cmdArgs.length < 5) {
                            System.out.println("Invalid number of arguments");
                            break;
                        }
                        String location = cmdArgs[3];
                        String time = cmdArgs[4];
                        createEvent(eventName, location, time);
                        break;

                    case "edit":
                        if (cmdArgs.length < 6) {
                            System.out.println("Invalid number of arguments");
                            break;
                        }
                        String newEventName = cmdArgs[3];
                        String newLocation = cmdArgs[4];
                        String newTime = cmdArgs[5];
                        editEvent(eventName, newEventName, newLocation, newTime);
                        break;

                    case "answer":
                        boolean isGoing;
                        if (cmdArgs[3].equalsIgnoreCase("y")) {
                            isGoing = true;
                        } else if (cmdArgs[3].equalsIgnoreCase("n")) {
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
                if (cmdArgs.length == 1) {
                    help();
                } else {
                    String commandName = cmdArgs[1];
                    help(commandName);
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
    private String[] splitArguments(String command) {
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
