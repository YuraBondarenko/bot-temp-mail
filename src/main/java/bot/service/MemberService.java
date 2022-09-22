package bot.service;

import bot.domain.Inbox;
import bot.domain.Member;
import bot.repo.MemberRepo;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;

import static bot.service.ApiEmailService.generateInbox;


public class MemberService {

    private final MemberRepo memberRepo;

    public MemberService(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    public Member getMember(Long memberId, Long chatId) {
        return memberRepo.getOne(Filters.and(Filters.eq("memberId", memberId), Filters.eq("chatId", chatId)));
    }

    public void add(Long memberId, Long chatId) {
        memberRepo.add(new Member(memberId, chatId));
    }

    public Inbox addInbox(Member member) {
        Inbox inbox = generateInbox();
        member.setInbox(inbox);
        memberRepo.update(Filters.and(Filters.eq("memberId", member.getMemberId()), Filters.eq("chatId", member.getChatId())), member);
        return inbox;
    }

    public void update(Member member) {
        memberRepo.update(Filters.and(Filters.eq("memberId", member.getMemberId()), Filters.eq("chatId", member.getChatId())), member);
    }

    public Inbox getInbox(Member member) {
        Member find = getMember(member.getMemberId(), member.getChatId());
        if (find != null) {
            return find.getInbox();
        }
        return addInbox(member);
    }

    public FindIterable<Member> getMembersForParsingMails() {
        return memberRepo.getMany(Filters.and(Filters.exists("inbox")));
    }

    public void deleteInbox(Member member) {
        memberRepo.deleteInbox(Filters.and(Filters.eq("memberId", member.getMemberId()), Filters.eq("chatId", member.getChatId())));
    }
}
