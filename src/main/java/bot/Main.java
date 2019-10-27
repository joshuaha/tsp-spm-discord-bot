package bot;

import factory.ServiceFactory;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import parse.MessageListener;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        new Main().init();
    }

    /**
     * Initializes bot.
     */
    private void init() {
        try {
            final Connection conn = ServiceFactory.getDatabaseService().getDatabaseConnection();
            final Statement stmt = conn.createStatement();
            final ResultSet set = stmt.executeQuery("SELECT * FROM TEST");
            while (set.next()) {
                System.out.println(set.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        final String token = this.loadToken();
        final ListenerAdapter messageListener = new MessageListener();
        try {
            new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .addEventListener(messageListener)
                    .buildAsync();
        } catch (LoginException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reads the token for the locally hosted bot from a properties file.
     *
     * @return the bot token located in the bot properties file
     */
    private String loadToken() {
        try {
            final Properties properties = new Properties();
            properties.load(new FileInputStream("bot.properties"));
            return (String) properties.get("token");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
