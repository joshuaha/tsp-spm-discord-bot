package factory;

import poll.DiscordPollDao;
import poll.DiscordPollDaoSql;

public class ServiceFactory {
    /**
     * Retrieve Discord poll dao instance
     * @return the Discord poll dao instance
     */
    public static DiscordPollDao getDiscordPollDao() {
        return new DiscordPollDaoSql();
    }

    /**
     * Retrieve database service instance
     * @return the database service instance
     */
    public static DatabaseService getDatabaseService() {
        return new DatabaseService();
    }
}
