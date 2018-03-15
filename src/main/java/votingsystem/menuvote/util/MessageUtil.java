package votingsystem.menuvote.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import votingsystem.menuvote.util.exception.ApplicationException;

import java.util.Locale;

@Component
public class MessageUtil {

    private final MessageSource messageSource;

    @Autowired
    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private String getMessage(String code, Locale locale, String... args) {
        return messageSource.getMessage(code, args, locale);
    }

    public String getMessage(String code, String... args) {
        return getMessage(code, LocaleContextHolder.getLocale(), args);
    }

    public String getMessage(ApplicationException appEx) {
        return getMessage(appEx.getMsgCode(), appEx.getArgs());
    }

}
