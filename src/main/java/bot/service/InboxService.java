//package service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.mongodb.client.MongoCollection;
//import config.MongoDbProperties;
//import domain.Inbox;
//import domain.Member;
//import org.bson.BsonDocument;
//import org.bson.Document;
//import org.springframework.stereotype.Service;
//import util.ObjectMapperUtils;
//
//import java.util.List;
//
//@Service
//public class InboxService {
//    private final MongoCollection<Document> inboxCollection = MongoDbProperties.getCollection("inbox");
//
//    public boolean exists(Member member) {
//        try {
//            String json = ObjectMapperUtils.getJsonFromObject(member);
//            Document oneAndUpdate = inboxCollection.findOneAndUpdate(new BsonDocument(), BsonDocument.parse(json));
//            return inboxCollection.find(Document.parse(json)).first() != null;
//        } catch (JsonProcessingException e) {
//            return false;
//        }
//    }
//
//    public void add(Member member, Inbox inbox) {
//        inbox.setMember(member);
//        inboxRepo.save(inbox);
//    }
//
//
//    //TODO
//    public Inbox getInbox(Member member) {
//        return inboxRepo.findById("member").get();
//    }
//
//
//    public void addNew(Member member, Inbox generateInbox) {
//        inboxRepo.delete(generateInbox);    //TODO
//        add(member, generateInbox);
//    }
//
//    public List<Inbox> getAllInboxes() {
//        return inboxRepo.findAll();
//    }
//
//    public void remove(Inbox inbox) {
//        inboxRepo.delete(inbox);
//    }
//}
