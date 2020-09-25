package utils._config.language;

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
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class CustomLocaleResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

	Logger logger = LoggerFactory.getLogger(CustomLocaleResolver.class);

	private String header = "Accept-Language";
	private String baseName = "classpath:language/messages";
	private String encoding = "UTF-8";
	private String[] locales = { "en", "es" };
	private String validationBaseName = "classpath:language/ValidationMessages";

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String headerLang = request.getHeader(header);
		Locale locale = headerLang == null || headerLang.isEmpty() ? Locale.getDefault()
				: Locale.lookup(Locale.LanguageRange.parse(headerLang), locales());
		return locale;
	}

	
	public MessageSource validatorMessage() {
		ReloadableResourceBundleMessageSource rs = new ReloadableResourceBundleMessageSource();
		rs.setBasename(validationBaseName);
		return rs;
	}

	@Bean
	public Validator validator() {
		LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
		factory.setValidationMessageSource(validatorMessage());
		return factory;
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
				hibernateProperties.put("javax.persistence.validation.factory", validator() );

				for (Map.Entry<String, Object> entry : hibernateProperties.entrySet())
					logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
		};
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource rs = new ReloadableResourceBundleMessageSource();
		rs.setBasename(baseName);
		rs.setDefaultEncoding(encoding);
		rs.setUseCodeAsDefaultMessage(true);
		return rs;
	}

	private List<Locale> locales() {
		List<Locale> localess = new ArrayList<Locale>();
		for (int i = 0; i < locales.length; i++) {
			localess.add(new Locale(locales[i]));
		}
		return localess;
	}

}