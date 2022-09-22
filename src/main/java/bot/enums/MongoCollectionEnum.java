package bot.enums;

import bot.domain.Member;

public enum MongoCollectionEnum {
    MEMBER("member", Member.class);

    private String collection;
    private Class<?> clazz;

    MongoCollectionEnum(String name, Class<?> clazz) {
        this.collection = name;
        this.clazz = clazz;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
