package dto;

import java.util.Objects;

public final class Member {
    private final Long id;
    private final Long chatId;

    public Member(Long id, Long chatId) {
        this.id = id;
        this.chatId = chatId;
    }

    public Long getId() {
        return id;
    }

    public Long getChatId() {
        return chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(chatId, member.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId);
    }
}
