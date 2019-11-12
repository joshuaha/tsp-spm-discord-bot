package poll;

import org.joda.time.LocalDateTime;

import java.util.*;

public class DiscordPoll {
    private static final int ID_LENGTH = 5;
    private static final String ID_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RNG = new Random();

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
        final int barWidthMax = 10;
        final int totalVotes = votes.stream().reduce(0, Integer::sum);
        final StringBuilder message = new StringBuilder();
        message.append("```");
        message.append(String.format("Poll ID: **%s**", poll.getId())).append(System.lineSeparator());
        message.append(poll.getText()).append(System.lineSeparator());
        for (int optionId = 0; optionId < options.size(); optionId++) {
            final String optionText = options.get(optionId);
            final int optionVotes = votes.get(optionId);
            final StringBuilder optionBar = new StringBuilder();
            final int optionBarWidth = (int)(((float)votes.get(optionId) / totalVotes) * barWidthMax);
            for (int i = 0; i < optionBarWidth; i++)
                optionBar.append('|');

            message.append(String.format("%d) %s :%" + barWidthMax + "s: %d votes", optionId, optionText, optionBar, optionVotes)).append(System.lineSeparator());
        }
        message.append("```");
        return message.toString();
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
