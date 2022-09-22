package bot.repo;

import bot.config.Initializer;
import bot.enums.MongoCollectionEnum;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import bot.domain.Member;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MemberRepo implements AbstractRepo<Member> {
    private final MongoCollection<Member> mongoCollection = (MongoCollection<Member>) Initializer.getCollection(MongoCollectionEnum.MEMBER);

    @Override
    public void add(Member member) {
        mongoCollection.insertOne(member);
    }

    @Override
    public Member getOne(Bson filter) {
        return mongoCollection.find(filter).first();
    }

    @Override
    public FindIterable<Member> getMany(Bson filter) {
        return mongoCollection.find(filter);
    }

    @Override
    public void update(Bson filter, Member member) {
        mongoCollection.updateOne(filter, new Document("$set", member));
    }

    @Override
    public void deleteInbox(Bson filter) {
        mongoCollection.updateOne(filter, new Document("$unset", new Document("inbox", null)));
    }
}
