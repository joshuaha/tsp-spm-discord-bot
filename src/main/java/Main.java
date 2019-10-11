import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main extends ListenerAdapter {
    public static void main(String[] args) {
        try {
            final Properties properties = new Properties();
            properties.load(new FileInputStream("bot.properties"));
            new JDABuilder(AccountType.BOT)
                    .setToken(String.valueOf(properties.get("token")))
                    .buildAsync();
        } catch(IOException | LoginException ex) {
            ex.printStackTrace();
        }
    }


}
