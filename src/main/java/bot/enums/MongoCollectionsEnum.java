package bot.enums;

import bot.domain.Member;

public enum MongoCollectionsEnum {
    member(Member.class);

    private Class<?> clazz;

    MongoCollectionsEnum(Class<?> memberClass) {

    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
