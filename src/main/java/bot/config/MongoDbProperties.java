package bot.config;

import bot.enums.MongoCollectionEnum;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import bot.domain.Member;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoDbProperties {
    private static final MongoDatabase mongoDatabase;
    private static final MongoClient mongoClient;

    static {
        mongoClient = new MongoClient(ConfigProperties.getProperty("MONGODB_URL"), Integer.parseInt(ConfigProperties.getProperty("MONGODB_PORT")));
        mongoDatabase = mongoClient.getDatabase(ConfigProperties.getProperty("MONGODB_NAME"))
                .withCodecRegistry(CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())));
        System.out.println("mongodb start success");
    }

    public static MongoCollection<?> getCollection(MongoCollectionEnum mongoCollectionEnum) {
        synchronized (mongoCollectionEnum.getCollection().intern()) {
            return mongoDatabase.getCollection(mongoCollectionEnum.getCollection(), mongoCollectionEnum.getClazz());
        }
    }
}
