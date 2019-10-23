package factory;

import poll.DiscordPollDao;
import poll.DiscordPollDaoSql;

public class ServiceFactory {
    public static DiscordPollDao getDiscordPollDao() {
        return new DiscordPollDaoSql();
    }

    public static DatabaseService getDatabaseService() {
        return new DatabaseService();
    }
}
