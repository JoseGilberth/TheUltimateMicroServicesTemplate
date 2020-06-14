package micro.usuarios._config.languaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class CustomLocaleResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

	Logger logger = LoggerFactory.getLogger(CustomLocaleResolver.class);

	@Value("${languaje.config.header}")
	private String header;

	@Value("${languaje.config.base-name}")
	private String baseName;

	@Value("${languaje.config.encoding}")
	private String encoding;

	@Value("${languaje.config.locales}")
	private String[] localess;

	@Value("${languaje.config.validation.base-name}")
	private String validationBaseName;
 
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String headerLang = request.getHeader(header);
		Locale locale = headerLang == null || headerLang.isEmpty() ? Locale.getDefault()
				: Locale.lookup(Locale.LanguageRange.parse(headerLang), locales());
		return locale;
	}

	// TRADUCCION Y MANEJO DE MENSAJES DESDE EL MODELO CON JPA
	@Bean
	@Lazy
	public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(final Validator validator) {
		ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure()
				.messageInterpolator(new ContextualMessageInterpolator(new ResourceBundleMessageInterpolator(
						new AggregateResourceBundleLocator(Arrays.asList(validationBaseName)))))
				.buildValidatorFactory();
		return new HibernatePropertiesCustomizer() {
			@Override
			public void customize(Map<String, Object> hibernateProperties) {
				hibernateProperties.put("javax.persistence.validation.factory", validatorFactory);
			}
		};
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource  rs = new ResourceBundleMessageSource ();
		rs.setBasenames(baseName);
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