package bot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import parse.MessageListener;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Run this file to start the Discord Bot.
 */
public class Main {
    public static void main(String[] args) {
        new Main().init();
    }

    /**
     * Initializes bot.
     */
    private void init() {
        final String token = this.getBotToken();
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
    private String getBotToken() {
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
