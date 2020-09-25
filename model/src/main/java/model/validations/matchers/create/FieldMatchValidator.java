package model.validations.matchers.create;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	
	Logger logger = LoggerFactory.getLogger(FieldMatchValidator.class);

	private String firstFieldName;
	private String secondFieldName;

	@Override
	public void initialize(final FieldMatch constraintAnnotation) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this); // work on localhost only..
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
	}
	
	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		try {
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			final Object password = BeanUtils.getProperty(value, firstFieldName);
			final Object repetirPassword = BeanUtils.getProperty(value, secondFieldName);  
 
			if (password.toString().equals(repetirPassword)) {
				return true;
			}
			if (bcrypt.matches(repetirPassword.toString(), password.toString())) {
				return true;
			}
		} catch (final Exception ignore) {
			// ignore
		}
		return false;
	}
}