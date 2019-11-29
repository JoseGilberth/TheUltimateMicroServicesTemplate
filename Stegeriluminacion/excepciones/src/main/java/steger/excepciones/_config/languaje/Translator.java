package steger.excepciones._config.languaje;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class Translator {

	public static String toLocale(String msgCode) {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("languaje/messages");
		messageSource.setDefaultEncoding("ISO-8859-1");
		messageSource.setUseCodeAsDefaultMessage(true);
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(msgCode, null, locale);
	}

}