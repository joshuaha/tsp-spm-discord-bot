package poll;

import command.CommandPollEdit;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import parse.TableBuilder;

import java.util.*;

public class DiscordPoll {
    public static final DateTimeFormatter DATE_OUTPUT_FORMAT = DateTimeFormat.forPattern("M-d-yyyy");
    public static final DateTimeFormatter TIME_OUTPUT_FORMAT = DateTimeFormat.forPattern("h:mma");

    private static final int ID_LENGTH = 5;
    private static final String ID_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RNG = new Random();
    private static final int BAR_WIDTH = 60;

    private String id;
    private long ownerId;
    private String text;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private long serverId;
    private long channelId;
    private long messageId;

    public static String getUniqueId() {
        final StringBuilder id = new StringBuilder();
        for (int i = 0; i < ID_LENGTH; i++) {
            id.append(ID_CHARS.charAt(RNG.nextInt(ID_CHARS.length())));
        }
        return id.toString();
    }

    public static String getDisplayMessage(DiscordPoll poll, List<String> options, List<Integer> votes) {
        final String openDateString = poll.getOpenTime().toString(DATE_OUTPUT_FORMAT);
        final String openTimeString = poll.getOpenTime().toString(TIME_OUTPUT_FORMAT);
        final String closeDateString = poll.getCloseTime().toString(DATE_OUTPUT_FORMAT);
        final String closeTimeString = poll.getCloseTime().toString(TIME_OUTPUT_FORMAT);
        return "```"
                + "Poll ID: " + poll.getId() + System.lineSeparator()
                + poll.getText() + System.lineSeparator()
                + getDisplayTable(poll, options, votes) + System.lineSeparator()
                + "Open time: " + openDateString + " at " + closeTimeString + System.lineSeparator()
                + "Close time: " + closeDateString + " at " + closeTimeString + System.lineSeparator()
                + "```";
    }

    private static String getDisplayTable(DiscordPoll poll, List<String> options, List<Integer> votes) {
        final int totalVotes = votes.stream().reduce(0, Integer::sum);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
