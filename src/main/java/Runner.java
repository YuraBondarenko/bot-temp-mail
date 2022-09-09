import config.ConfigProperties;
import service.BotService;

public class Runner {
    public static void main(String[] args) {
        ConfigProperties.load();
        new BotService().start();
    }
}
