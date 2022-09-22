package bot.db;

import bot.domain.Inbox;
import bot.domain.Member;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class Storage {
    private static final Map<Member, Inbox> storage = new HashMap<>();

    public static void addOrUpdateInbox(Member member, Inbox inbox) {
        storage.put(member, inbox);
    }


    public static void remove(Member member) {
        storage.remove(member);
    }

    public static Inbox getInbox(Member member) {
        return storage.get(member);
    }

    public static Map<Member, Inbox> getKeys() {
        return storage;
    }

    public static boolean contains(Member member) {
        return storage.containsKey(member);
    }
}
