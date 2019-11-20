package poll;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import parse.TableBuilder;

import java.util.List;

public class DiscordPollFormatter {
    public static final DateTimeFormatter DATE_OUTPUT_FORMAT = DateTimeFormat.forPattern("M-d-yyyy");
    public static final DateTimeFormatter TIME_OUTPUT_FORMAT = DateTimeFormat.forPattern("h:mma");
    private static final int BAR_WIDTH = 60;

    public static String getDisplayMessage(DiscordPoll poll, List<String> options, List<Integer> votes) {
        final String openDateString = poll.getOpenTime().toString(DATE_OUTPUT_FORMAT);
        final String openTimeString = poll.getOpenTime().toString(TIME_OUTPUT_FORMAT);
        final String closeDateString = poll.getCloseTime().toString(DATE_OUTPUT_FORMAT);
        final String closeTimeString = poll.getCloseTime().toString(TIME_OUTPUT_FORMAT);
        int sum = 0;
        for (Integer i : votes)
            sum += i;
        final int totalVotes = sum;
        return "```"
                + "Poll ID: " + poll.getId() + System.lineSeparator()
                + "-----" + System.lineSeparator()
                + poll.getText() + System.lineSeparator()
                + "-----" + System.lineSeparator()
                + totalVotes + (totalVotes == 1 ? " vote has " : " votes have ") + "been cast" + System.lineSeparator()
                + getDisplayTable(poll, options, votes) + System.lineSeparator()
                + "Open time: " + openDateString + " at " + openTimeString + System.lineSeparator()
                + "Close time: " + closeDateString + " at " + closeTimeString + System.lineSeparator()
                + "```";
    }

    private static String getDisplayTable(DiscordPoll poll, List<String> options, List<Integer> votes) {
        int sum = 0;
        for (Integer i : votes)
            sum += i;
        final int totalVotes = sum;
        final TableBuilder table = new TableBuilder(options.size());
        final Object[] column = new String[options.size()];
        for (int i = 0; i < options.size(); i++)
            column[i] = (i + 1) + ") ";
        table.append(column);
        for (int i = 0; i < options.size(); i++)
            column[i] = options.get(i);
        table.append(column);
        for (int i = 0; i < options.size(); i++)
            column[i] = " :";
        table.append(column);
        for (int i = 0; i < options.size(); i++) {
            final int barWidth = Math.round(((float) votes.get(i) / totalVotes) * BAR_WIDTH);
            final StringBuilder bar = new StringBuilder();
            for (int j = 0; j < BAR_WIDTH; j++)
                bar.append(j < barWidth ? '|' : ' ');
            column[i] = bar.toString();
        }
        table.append(column);
        for (int i = 0; i < options.size(); i++)
            column[i] = ": ";
        table.append(column);
        for (int i = 0; i < options.size(); i++)
            column[i] = votes.get(i) + " vote" + (votes.get(i) != 1 ? "s" : " ");
        table.append(column);
        return table.toString();
    }
}
