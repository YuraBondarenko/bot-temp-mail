package bot.domain;

import java.util.Objects;

public class Member {
    private Long memberId;
    private Long chatId;
    private Inbox inbox;

    public Member() {
    }

    public Member(Long memberId, Long chatId) {
        this.memberId = memberId;
        this.chatId = chatId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getChatId() {
        return chatId;
    }

    public Inbox getInbox() {
        return inbox;
    }

    public void setInbox(Inbox inbox) {
        this.inbox = inbox;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(memberId, member.memberId) && Objects.equals(chatId, member.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, chatId);
    }
}
