package factory;

import poll.DiscordPollDao;
import poll.DiscordPollDaoLocal;

public class DaoFactory {
    private static final DiscordPollDao DISCORD_POLL_DAO = new DiscordPollDaoLocal();

    public static DiscordPollDao getDiscordPollDao() {
        return DISCORD_POLL_DAO;
    }
}
