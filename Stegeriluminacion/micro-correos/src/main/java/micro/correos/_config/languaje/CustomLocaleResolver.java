package micro.correos._config.languaje;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class CustomLocaleResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

	@Value("${languaje.config.header}")
	private String header;

	@Value("${languaje.config.base-name}")
	private String baseName;

	@Value("${languaje.config.encoding}")
	private String encoding;

	@Value("${languaje.config.locales}")
	private String[] localess;

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String headerLang = request.getHeader(header);
		return headerLang == null || headerLang.isEmpty() ? Locale.getDefault() : Locale.lookup(Locale.LanguageRange.parse(headerLang), locales());
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
		rs.setBasename(baseName);
		rs.setDefaultEncoding(encoding);
		rs.setUseCodeAsDefaultMessage(true);
		return rs;
	}

	private List<Locale> locales() {
		List<Locale> locales = new ArrayList<Locale>();
		for (int i = 0; i < localess.length; i++) {
			locales.add(new Locale(localess[i]));
		}
		return locales;
	}

}