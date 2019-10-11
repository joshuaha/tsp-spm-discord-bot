import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        final Properties properties = new Properties();
        properties.load(new FileInputStream("bot.properties"));
        System.out.println(properties.get("token"));
    }
}
