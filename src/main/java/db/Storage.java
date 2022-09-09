package db;

import dto.Inbox;
import dto.Member;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static final Map<Member, Inbox> map = new HashMap<>();

    public static void addOrUpdateInbox(Member member, Inbox inbox) {
        map.put(member, inbox);
    }


    public static void remove(Member member) {
        map.remove(member);
    }

    public static Inbox getInbox(Member member) {
        return map.get(member);
    }

    public static Map<Member, Inbox> getKeys() {
        return map;
    }

    public static boolean contains(Member member) {
        return map.containsKey(member);
    }
}
