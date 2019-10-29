package poll;

import org.joda.time.LocalDateTime;

import java.util.Random;

public class DiscordPoll {
    private static final int ID_LENGTH = 5;
    private static final String ID_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RNG = new Random();

    private String id;
    private long owner;
    private String text;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;

    public static String getUniqueId() {
        final StringBuilder id = new StringBuilder();
        for (int i = 0; i < ID_LENGTH; i++) {
            id.append(ID_CHARS.charAt(RNG.nextInt(ID_CHARS.length())));
        }
        return id.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
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
}
