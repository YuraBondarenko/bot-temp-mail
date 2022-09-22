package bot.config;

import bot.domain.Member;
import bot.enums.ExceptionEnum;
import bot.enums.MongoCollectionEnum;
import bot.exception.BotException;
import com.mongodb.client.MongoCollection;
import com.pengrad.telegrambot.TelegramBot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Initializer {

    private static TelegramBot telegramBot;
    private static final Map<String, MongoCollection<?>> collectionMap = new HashMap<>();


    public static void init() {
        if (telegramBot == null) {
            try {
                telegramBot = new TelegramBot(ConfigProperties.getProperty("BOT_API_KEY"));
            } catch (Exception e) {
                e.printStackTrace();
                throw new BotException(ExceptionEnum.botNotStarted);
            }
        }
    }

    public static TelegramBot getTelegramBot() {
        return telegramBot;
    }

    public static MongoCollection<?> getCollection(MongoCollectionEnum mongoCollectionEnum) {
        if (collectionMap.containsKey(mongoCollectionEnum.getCollection())) {
            return collectionMap.get(mongoCollectionEnum.getCollection());
        }
        MongoCollection<?> collection = MongoDbProperties.getCollection(mongoCollectionEnum);
        collectionMap.put(mongoCollectionEnum.getCollection(), collection);
        return collection;
    }
}
