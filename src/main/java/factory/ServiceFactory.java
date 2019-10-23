package factory;

import poll.DiscordPollDao;
import poll.DiscordPollDaoSql;

public class ServiceFactory {
    private static final DiscordPollDao DISCORD_POLL_DAO = new DiscordPollDaoSql();
    private static final DatabaseService DATABASE_SERVICE = new DatabaseService();

    public static DiscordPollDao getDiscordPollDao() {
        return DISCORD_POLL_DAO;
    }

    public static DatabaseService getDatabaseService() {
        return DATABASE_SERVICE;
    }
}
