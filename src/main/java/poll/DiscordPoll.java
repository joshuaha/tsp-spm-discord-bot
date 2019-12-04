package poll;

import command.CommandPollEdit;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import parse.TableBuilder;

import java.util.*;

public class DiscordPoll {
    private static final int ID_LENGTH = 5;
    // All characters that one digit of an ID can be. //
    private static final String ID_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // Used in the generation of a unique ID. //
    private static final Random RNG = new Random();

    private String id;
    private long ownerId;
    private String text;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private long serverId;
    private long channelId;
    private long messageId;

    /**
     * Returns a unique poll ID
     * @return the poll ID
     */
    public static String getUniqueId() {
        // Generates a random 5 length number that acts as the poll's ID. //
        // The poll ID isn't actually guaranteed to be unique we just generate a random string and hope for the best
        final StringBuilder id = new StringBuilder();
        for (int i = 0; i < ID_LENGTH; i++) {
            id.append(ID_CHARS.charAt(RNG.nextInt(ID_CHARS.length())));
        }
        return id.toString();
    }
    // The following are getters and setters for information important to polls. //

    // Retrieves a Poll's ID, sets a poll's ID. //
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    // Retrieves a Discord user's ID, sets to a Discord user's ID. //
    public long getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    // Retrieves the name / question of the poll, sets the name / question of the poll. //
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    // Retrieves the time that the poll begins, sets the time that the poll begins. //
    public LocalDateTime getOpenTime() {
        return openTime;
    }
    public void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }

    // Retrieves the time that the poll ends, sets the time that the poll ends. //
    public LocalDateTime getCloseTime() {
        return closeTime;
    }
    public void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }

    // Retrieves the ID of the server that the poll is created in, sets the ID of the server the poll is created in. //
    public long getServerId() {
        return serverId;
    }
    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    // Retrieves the ID of the channel that the poll is created in, sets the ID of the channel the poll is created in. //
    public long getChannelId() {
        return channelId;
    }
    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    // Retrieves the ID of the user's message, sets the ID of the user's message. //
    public long getMessageId() {
        return this.messageId;
    }
    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
