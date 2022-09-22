import bot.config.ConfigProperties;
import bot.config.Initializer;
import bot.repo.MemberRepo;
import bot.service.MemberService;
import bot.service.bot.BotService;

public class Runner {
    public static void main(String[] args) {
        ConfigProperties.load();
        Initializer.init();

        MemberRepo memberRepo = new MemberRepo();
        MemberService memberService = new MemberService(memberRepo);
        new BotService(memberService).start();
    }
}
