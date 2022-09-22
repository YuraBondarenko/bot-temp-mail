package bot.exception;

import bot.enums.ExceptionEnum;

public class BotException extends RuntimeException {

    public BotException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.name());
    }
}
