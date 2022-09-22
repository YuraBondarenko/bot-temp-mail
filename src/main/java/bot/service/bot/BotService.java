package bot.service.bot;

import bot.config.Initializer;
import bot.domain.Inbox;
import bot.domain.Member;
import bot.dto.Email;
import bot.service.MemberService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static bot.service.ApiEmailService.getEmails;
import static bot.service.bot.BotMessageSender.sendMessage;

public class BotService {
    private final MemberService memberService;
    private final TelegramBot bot;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public BotService(MemberService memberService) {
        this.memberService = memberService;
        bot = Initializer.getTelegramBot();
    }

    public void start() {
        schedulerParseEmails();
        bot.setUpdatesListener(updates -> {
            updates.forEach(this::processMessage);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void processMessage(Update update) {
        Message message = update.message();
        Long chatId = message.chat().id();
        Long memberId = message.from().id();
        synchronized ((memberId + " " + chatId).intern()) {
            Member member = memberService.getMember(memberId, chatId);
            if (member == null) {
                memberService.add(memberId, chatId);
                member = memberService.getMember(memberId, chatId);
            }
            if ("/create".equals(message.text())) {
                if (member.getInbox() == null) {
                    memberService.addInbox(member);
                }
                sendMessage(chatId, "Your temp mail " + memberService.getInbox(member).getAddress());
            } else if ("/new".equals(message.text())) {
                Inbox inbox = memberService.addInbox(member);
                sendMessage(chatId, "Your new temp mail " + inbox.getAddress());
            } else if ("/get".equals(message.text())) {
                if (member.getInbox() != null) {
                    sendMessage(chatId, "Your temp mail " + member.getInbox().getAddress());
                } else {
                    sendMessage(chatId, "Your don't have temp mail. Type /create to get new temp mail.");
                }
            }
        }
    }

    private void schedulerParseEmails() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    for (Member member : memberService.getMembersForParsingMails()) {
                        Inbox inbox = member.getInbox();
                        List<Email> emails = getEmails(inbox);
                        if (emails == null) {
                            memberService.deleteInbox(member);
                            sendMessage(member.getChatId(), "Your temp mail is inactive. Create new by /create.");
                            return;
                        }
                        emails.forEach(email ->
                                sendMessage(member.getChatId(), "To: " + inbox.getAddress()
                                        + "\nFrom: " + email.getFromAddress() + "\nSubject: " + email.getSubject()
                                        + "\nDate: " + sdf.format(new Date(email.getDate()))
                                        + "\n\n" + email.getBody()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000, 5000);
    }

}
