package micro.auth._config.languaje;

import java.util.Locale;

import javax.validation.MessageInterpolator;

import org.springframework.context.i18n.LocaleContextHolder;

public class ContextualMessageInterpolator implements MessageInterpolator {
	private final MessageInterpolator delegate;

	public ContextualMessageInterpolator(MessageInterpolator delegate) {
		this.delegate = delegate;
	}

	@Override
	public String interpolate(String message, Context context) {
		Locale locale = LocaleContextHolder.getLocale();
		return this.delegate.interpolate(message, context, locale);
	}
                                                                                
	@Override
	public String interpolate(String message, Context context, Locale locale) {
		return this.delegate.interpolate(message, context, locale);
	}
}