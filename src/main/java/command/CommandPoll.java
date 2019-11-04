package command;

import com.mysql.jdbc.StringUtils;
import factory.ServiceFactory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.joda.time.LocalDateTime;
import poll.DiscordPoll;
import poll.DiscordPollDao;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


public class CommandPoll implements Command {
    private final DiscordPollDao pollDao = ServiceFactory.getDiscordPollDao();

    CommandPoll() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAlias() {
        return "poll";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        if (args.length == 0) {
            event.getChannel().sendMessage("Invalid command. Type \"!help\" for help.").queue();
            return;
        }

        if ("create".equals(args[0]) && args.length > 1) {
            //args.length > 1 is to check that "!poll create" wasn't the only thing entered.

            String pollID = DiscordPoll.getUniqueId();
            final String text = args[1];
            final long ownerId = event.getAuthor().getIdLong();
            final String[] options = Arrays.copyOfRange(args, 2, args.length);
            //TODO - Don't allow empty string options.
            if (options.length < 1 || StringUtils.isEmptyOrWhitespaceOnly(text)) {
                event.getChannel().sendMessage("Unable to create poll. Type \"!help\" for help.").queue();
            } else if (this.createPoll(ownerId, text, options, pollID)) {
                event.getChannel().sendMessage("Poll created successfully.").queue();
                event.getChannel().sendMessage("Your Poll ID is " + pollID + ".").queue();
            } else {
                event.getChannel().sendMessage("Unable to create poll. Type \"!help\" for help.").queue();
            }

        } else if ("vote".equals(args[0])) {

            final String pollId = args[1];
            final int vote = Integer.parseInt(args[2]) - 1;
            final long userId = event.getAuthor().getIdLong();
            this.setVote(pollId, userId, vote, event);
            //TODO - Let user know vote is index not name of option.

        } else if ("results".equals(args[0])) {

            final String pollId = args[1];
            this.getResults(pollId, event);

        } else if ("edit".equals(args[0])) {

            final String pollName = args[1];
            final String edit = args[2];
            if ("name".equalsIgnoreCase(edit)) {

                this.pollDao.getPoll(pollName).setText(args[3]);

            }
            //Editing options resets the voting
            else if ("option".equalsIgnoreCase(edit)) {

                int optionIndex = Integer.parseInt(args[3]);
                final String newOption = args[4];

                //Adding new option
                if ( optionIndex > this.pollDao.getOptions( pollName ).size() ) {

                    //Stop users from breaking the bot
                    if (optionIndex > Integer.MAX_VALUE) optionIndex = Integer.MAX_VALUE;

                    if (optionIndex <= 0) {
                        final StringBuilder message = new StringBuilder();
                        message.append("Poll indexes begin at 1");
                        event.getMessage().getAuthor().openPrivateChannel().queue((channel)
                                -> channel.sendMessage(message).queue());
                        return;
                    }


                    List<String> options = this.pollDao.getOptions(pollName);
                    List<String> newOptions = new ArrayList<>();
                    //Copy the old poll options
                    for ( String str : options ) {
                        newOptions.add( str );
                    }
                    newOptions.add( newOption );

                    this.pollDao.setOptions( pollName, newOptions );

                    event.getChannel().sendMessage( "Options were updated for " +
                            this.pollDao.getPoll( pollName).getText() + " Poll results have been reset." );

                }
                //Edit existing option
                else {
                    List<String> options = this.pollDao.getOptions(pollName);
                    options.set( optionIndex, newOption );
                    this.pollDao.setOptions( pollName, options );

                    event.getChannel().sendMessage( "Options were updated for " +
                            this.pollDao.getPoll( pollName).getText() + " Poll results have been reset." );
                }

            } //End option editing

        } else {
            event.getChannel().sendMessage("Invalid command. Type \"!help\" for help.").queue();
            return;
        }
    }

    private boolean createPoll(long ownerId, String text, String[] options, String pollID) {
        final DiscordPoll poll = new DiscordPoll();
        poll.setId(pollID);
        poll.setText(text);
        poll.setOwnerId(ownerId);
        poll.setOpenTime(LocalDateTime.now());
        poll.setCloseTime(LocalDateTime.now().plusDays(1));
        return this.pollDao.createPoll(poll) && this.pollDao.setOptions(poll.getId(), Arrays.asList(options));
    }

    private boolean setVote(String pollId, long userId, int vote, MessageReceivedEvent event) {
        return this.pollDao.setVote(pollId, userId, vote);
    }

    private void getResults(String pollId, MessageReceivedEvent event) {
        final DiscordPoll poll = this.pollDao.getPoll(pollId);
        final List<Integer> votes = new ArrayList<>();
        final List<String> options = this.pollDao.getOptions(poll.getId());
        for (int i = 0; i < options.size(); i++) {
            votes.add(this.pollDao.getVotes(poll.getId(), i));
        }
        //assemble the output message
        final StringBuilder message = new StringBuilder();
        message.append(poll.getId())
                .append(System.lineSeparator())
                .append(poll.getText())
                .append(System.lineSeparator());
        for (int i = 0; i < options.size(); i++) {
            message.append(String.format("%s: %d", options.get(i), votes.get(i)))
                    .append(System.lineSeparator());
        }
        //print the output message
        event.getChannel().sendMessage(message.toString()).queue();
    }
}
