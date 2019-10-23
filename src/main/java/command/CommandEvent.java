package command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandEvent implements Command {
    CommandEvent() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAlias() {
        return "event";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        if ( "create".equals( args[0] ) ) {
            final String eventName = args[1];
            final String eventLoc = args[2];
            final String eventTime = args[3];
            this.create( eventName, eventLoc, eventTime );

        } else if ( "answer".equals( args[0] ) ) {
            final String eventName = args[1];
            final String going = args[2];
            boolean isGoing = false;
            if ( going.equals( "y" ) ) {
                isGoing = true;
            } else if ( going.equals( "n" ) ) {
                isGoing = false;
            }
            final long user = event.getAuthor().getIdLong();
            this.answer( eventName, isGoing, user, event );

        } else if ("results".equals(args[0])) {

            final String eventName = args[1];
            this.results( eventName, event );

        } else if ( "edit".equals( args[0] ) ) {
            final String eventName = args[1];
            final String edit = args[2];
            if ( "name".equalsIgnoreCase( edit ) ) {

            } else if ( "location".equalsIgnoreCase( edit ) ) {

            } else if ( "time".equalsIgnoreCase( edit ) ) {

            }

        } else {
            //TODO - Send message saying invalid command and give them help.
            return;
        }
    }

    private void create ( String name, String location, String time ) {

    }

    private void results ( String name, MessageReceivedEvent event ) {

    }

    private void editName( String name, String newName ) {

    }

    private void editLocation ( String name, String newLocation ) {

    }

    private void editTime ( String name, String newTime ) {

    }

    private void answer ( String name, boolean isGoing, long user, MessageReceivedEvent event ) {

    }
}
