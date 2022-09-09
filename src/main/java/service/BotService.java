package service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import db.Storage;
import dto.Email;
import dto.Inbox;
import dto.Member;

import java.text.SimpleDateFormat;
import java.util.*;

public class BotService {
    private final EmailService emailService = new EmailService();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    TelegramBot bot = new TelegramBot(System.getenv("BOT_API_KEY") != null ? System.getenv("BOT_API_KEY") : System.getProperty("BOT_API_KEY"));

    public void start() {
        bot.setUpdatesListener(updates -> {
            updates.forEach(update -> {
                Member member = new Member(update.message().from().id(), update.message().chat().id());
                if ("/create".equals(update.message().text())) {
                    if (!Storage.contains(member)) {
                        Storage.addOrUpdateInbox(member, emailService.generateInbox());
                    }
                    sendMessage(update.message().chat().id(), "Your temp mail " + Storage.getInbox(member).getAddress());
                } else if ("/new".equals(update.message().text())) {
                    Storage.addOrUpdateInbox(member, emailService.generateInbox());
                    sendMessage(update.message().chat().id(), "Your new temp mail " + Storage.getInbox(member).getAddress());
                } else if ("/get".equals(update.message().text())) {
                    if (Storage.contains(member)) {
                        sendMessage(update.message().chat().id(), "Your temp mail " + Storage.getInbox(member).getAddress());
                    } else {
                        sendMessage(update.message().chat().id(), "Your don't have temp mail. Type /create to get new temp mail.");
                    }
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Map<Member, Inbox> map = Storage.getKeys();
                    map.forEach((member, inbox) -> {
                        List<Email> emails = emailService.getEmails(inbox);
                        if (emails == null) {
                            Storage.remove(member);
                            sendMessage(member.getChatId(), "Your temp mail is inactive. Create new");
                            return;
                        }
                        Date date = new Date();
                        emails.forEach(email ->
                                bot.execute(new SendMessage(member.getChatId(), "To: " + inbox.getAddress() + "\nFrom: " + email.getFromAddress() + "\nSubject: " + email.getSubject()
                                        + "\nDate: " + sdf.format(new Date(email.getDate()))
                                        + "\n\n" + email.getBody())));
                        inbox.setLastDate(date);
                        Storage.addOrUpdateInbox(member, inbox);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000, 5000);
    }

    private void sendMessage(Long update, String text) {
        bot.execute(new SendMessage(update, text));
    }

}
