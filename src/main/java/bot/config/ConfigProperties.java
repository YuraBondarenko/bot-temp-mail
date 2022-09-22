package bot.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {
    public static Properties prop = new Properties();

    public static void load() {
        InputStream input = null;
        try {
            input = ConfigProperties.class.getClassLoader().getResourceAsStream("application.properties");
            prop.load(input);
            System.out.println("success load properties");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
