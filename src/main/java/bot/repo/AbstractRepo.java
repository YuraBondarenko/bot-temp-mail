package bot.repo;

import com.mongodb.client.FindIterable;
import org.bson.conversions.Bson;


public interface AbstractRepo<T> {

    void add(T t);

    T getOne(Bson filter);

    FindIterable<T> getMany(Bson filter);

    void update(Bson filter, T t);

    void deleteInbox(Bson filter);

}
