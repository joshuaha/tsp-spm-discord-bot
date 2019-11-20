package command;

import factory.ServiceFactory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import poll.DiscordPoll;
import poll.DiscordPollDao;
import poll.DiscordPollFormatter;

import java.util.ArrayList;
import java.util.List;

public class CommandPollEdit implements Command {
    private static final DateTimeFormatter DATE_TIME_INPUT_FORMAT = DateTimeFormat.forPattern("M-d-yy h:mma");
    private static final DateTimeFormatter DATE_INPUT_FORMAT = DateTimeFormat.forPattern("M-d-yy");
    private static final DateTimeFormatter TIME_INPUT_FORMAT = DateTimeFormat.forPattern("h:mma");
    private final DiscordPollDao pollDao = ServiceFactory.getDiscordPollDao();

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAlias() {
        return "edit";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        final DiscordPoll poll = pollDao.getPoll(args[0]);
        if (event.getAuthor().getIdLong() == poll.getOwnerId()) {
            //get the property the user wishes to edit
            final String property = args[1];
            if ("text".equalsIgnoreCase(property)) {
                //update the poll text
                poll.setText(args[2]);
                this.pollDao.updatePoll(poll);
                event.getChannel().sendMessage("Successfully edited poll text.").queue();
            } else if ("option".equalsIgnoreCase(property)) {
                final String option = args[3];
                final List<String> options = new ArrayList<>(this.pollDao.getOptions(poll.getId()));
                if (args[2].equalsIgnoreCase("add")) {
                    //if add is called after option add a new option.
                    options.add(option);
                    this.pollDao.setOptions(poll.getId(), options);
                    event.getChannel().sendMessage(String.format("Option %d has been successfully added. " + "Poll results have been reset", options.size())).queue();
                } else {
                    final int index = Integer.parseInt(args[2]) - 1;
                    //check if index is within the valid range
                    if (index >= 0 && index <= options.size()) {
                        //if the index matches an existing option, overwrite that option
                        options.set(index, option);
                        this.pollDao.setOptions(poll.getId(), options);
                        event.getChannel().sendMessage(String.format("Option %d has been successfully changed. " + "Poll results have been reset.", index + 1)).queue();
                    } else {
                        //report if the provided index was invalid
                        event.getChannel().sendMessage(String.format("%d is not a valid index.", index + 1)).queue();
                    }
                }
            } else if ("opentime".equalsIgnoreCase(property)) {
                //update the poll open time

                //Check if just date.
                try {
                    LocalTime time = LocalTime.parse( args[2] ); //TODO - Add input format when pulled from repo.
                    //convert to date time.
                } catch ( Exception e ) {
                    System.out.println( "Not a valid time format. Exception: " + e );
                }
                //check if just time.
                try {
                    LocalDate date = LocalDate.parse( args[2] ); //TODO - Add input format when pulled from repo.
                    //create date time where it starts at midnight
                } catch ( Exception e ) {
                    System.out.println( "Not a valid date format. Exception: " + e );
                }
                //check if it is a correctly formatted LocalDateTime
                try {
                    LocalDateTime dateTime = LocalDateTime.parse( args[2], DATE_TIME_INPUT_FORMAT );
                } catch ( Exception e ) {

                }
                // IF NONE OF THESE FORMATS SEND THEM THIS MESSAGE
                event.getChannel().sendMessage( "Please enter a valid date and time. " +
                        "Type !help edit openTime" ).queue();

                final LocalDateTime openTime = LocalDateTime.parse(args[2], DATE_TIME_INPUT_FORMAT);

                poll.setOpenTime(openTime);
                this.pollDao.updatePoll(poll);
                final String openDateString = poll.getOpenTime().toString(DiscordPollFormatter.DATE_OUTPUT_FORMAT);
                final String openTimeString = poll.getOpenTime().toString(DiscordPollFormatter.TIME_OUTPUT_FORMAT);
                event.getChannel().sendMessage(String.format("Poll open time successfully update. Poll now begins on %s at %s.", openDateString, openTimeString)).queue();
            } else if ("closetime".equalsIgnoreCase(property)) {
                //update the poll close time
                final LocalDateTime closeTime = LocalDateTime.parse(args[2], DATE_TIME_INPUT_FORMAT);
                poll.setCloseTime(closeTime);
                this.pollDao.updatePoll(poll);
                final String closeDateString = poll.getCloseTime().toString(DiscordPollFormatter.DATE_OUTPUT_FORMAT);
                final String closeTimeString = poll.getCloseTime().toString(DiscordPollFormatter.TIME_OUTPUT_FORMAT);
                event.getChannel().sendMessage(String.format("Poll end time successfully update. Poll now ends on %s at %s", closeDateString, closeTimeString)).queue();
            }
            //update poll message
            final List<String> options = this.pollDao.getOptions(poll.getId());
            final List<Integer> votes = this.pollDao.getVotes(poll.getId());
            event.getChannel().editMessageById(poll.getMessageId(), DiscordPollFormatter.getDisplayMessage(poll, options, votes)).queue();
        } else {
            event.getChannel().sendMessage("You do not have permission to edit this poll.").queue();
        }
    }
}
