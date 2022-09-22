package bot.service.bot;

import bot.config.Initializer;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

public class BotMessageSender {
    private static final TelegramBot bot = Initializer.getTelegramBot();

    public static void sendMessage(Long id, String text) {
        bot.execute(new SendMessage(id, text));
    }
}
