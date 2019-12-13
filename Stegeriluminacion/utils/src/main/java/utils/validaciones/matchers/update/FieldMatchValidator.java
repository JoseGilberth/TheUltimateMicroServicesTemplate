package utils.validaciones.matchers.update;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class FieldMatchValidator implements ConstraintValidator<FieldMatchUpdate, Object> {
	
	Logger logger = LoggerFactory.getLogger(FieldMatchValidator.class);

	private String firstFieldName;
	private String secondFieldName;

	@Override
	public void initialize(final FieldMatchUpdate constraintAnnotation) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
	}
	
	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		try { 
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			final Object password = BeanUtils.getProperty(value, firstFieldName);
			final Object repetirPassword = BeanUtils.getProperty(value, secondFieldName);  
			
			logger.info("password: " + password);
			logger.info("repetirPassword: " + repetirPassword);
			
			if (repetirPassword.toString().equals("") && password.toString().length() > 0) {
				return true;
			}
			if (repetirPassword.toString().equals("") && password.toString().equals("") ) {
				return true;
			}
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