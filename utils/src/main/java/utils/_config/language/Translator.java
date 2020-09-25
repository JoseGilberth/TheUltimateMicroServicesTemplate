package utils._config.language;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class Translator {

	private static ReloadableResourceBundleMessageSource messageSource;

	static Logger logger = LoggerFactory.getLogger(Translator.class);

	@Autowired
	Translator(ReloadableResourceBundleMessageSource messageSource) {
		Translator.messageSource = messageSource;
	}

	public static String toLocale(String msgCode) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(msgCode, null, locale);
	}

	public static String toLocale(String msgCode, Object[] args) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(msgCode, args, locale);
	}
}