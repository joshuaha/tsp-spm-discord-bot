package command;

import factory.ServiceFactory;
import org.joda.time.LocalDateTime;
import poll.DiscordPoll;
import poll.DiscordPollOld;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import poll.DiscordPollDao;
import poll.DiscordPollDaoSql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandPoll implements Command {
    private final DiscordPollDao pollDao  = ServiceFactory.getDiscordPollDao();

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
        if ("create".equals(args[0])) {

            final String pollName = args[1];
            final long owner = event.getAuthor().getIdLong();
            final String[] options = Arrays.copyOfRange(args, 2, args.length);
            this.create(pollName, owner, options);
        } else if ("vote".equals(args[0])) {

            final String pollName = args[1];
            final int vote = Integer.parseInt(args[2]) - 1;
            final long user = event.getAuthor().getIdLong();
            this.vote(pollName, user, vote, event);
            //TODO - Let user know vote is index not name of option.

        } else if ("results".equals(args[0])) {

            final String pollName = args[1];
            this.results(pollName, event);

        } else if ( "edit".equals( args[0] ) ) {

            final String pollName = args[1];
            final String edit = args[2];
            if ( "name".equalsIgnoreCase( edit ) ) {

                this.pollDao.getPoll( pollName ).setName( args[3] );

            } else if ( "option".equalsIgnoreCase( edit ) ) {

                int optionIndex = Integer.parseInt( args[3] );
                final String newOption = args[4];

                if ( optionIndex > this.pollDao.getPoll(pollName).getOptions().size() ) {

                    if ( optionIndex > Integer.MAX_VALUE ) optionIndex = Integer.MAX_VALUE;
                    if ( optionIndex <= 0 ) {
                        final StringBuilder message = new StringBuilder();
                        message.append( "Poll indexes begin at 1" );
                        event.getMessage().getAuthor().openPrivateChannel().queue((channel)
                                -> channel.sendMessage(message).queue());
                        return;
                    }

                    List<DiscordPoll.Option> options = this.pollDao.getPoll( pollName ).getOptions();
                    List<String> newOptions = new ArrayList<>();
                    for (DiscordPoll.Option o : options ) {
                        newOptions.add( o.getText() );
                    }
                    newOptions.add( newOption );

                    this.pollDao.getPoll( pollName ).setOptions( newOptions );

                    event.getChannel().sendMessage( this.pollDao.getPoll( pollName).getName()
                            + "'s options were updated." );
                }

                //TODO - Edit existing option.
                //TODO - Reset results and notify that poll results are reset for X Option.

            }

        } else {
            //TODO - Send message saying invalid command and give them help.
            return;
        }
    }

    private void create(String name, long owner, String[] options) {
        final DiscordPoll poll = new DiscordPoll();
        poll.setId(DiscordPoll.getUniqueId());
        poll.setOwner(owner);
        poll.setOpenTime(LocalDateTime.now());
        poll.setCloseTime(LocalDateTime.now().plusDays(1));
    }

    private void vote(String pollName, long user, int vote, MessageReceivedEvent event) {
        this.pollDao.setVote(pollName, user, vote);
    }

    private void results(String pollName, MessageReceivedEvent event) {
        final DiscordPollOld poll = null;
        final StringBuilder message = new StringBuilder();
        message.append(poll.getName()).append(System.lineSeparator());
        for (DiscordPollOld.Option option : poll.getOptions()) {
            message.append(option.getText())
                    .append(": ")
                    .append(option.getVotes())
                    .append(System.lineSeparator());
        }
        //event.getMessage().delete().queue();
        event.getMessage().getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(message).queue());
    }
}
