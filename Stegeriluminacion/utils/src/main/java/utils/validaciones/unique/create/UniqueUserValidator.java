package utils.validaciones.unique.create;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UniqueUserValidator implements ConstraintValidator<UniqueUser, Object> {
	
	Logger logger = LoggerFactory.getLogger(UniqueUserValidator.class);

	private String firstFieldName;
	private String secondFieldName;
	 
	  
	@Override
	public void initialize(final UniqueUser constraintAnnotation) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		firstFieldName = constraintAnnotation.username();
		secondFieldName = constraintAnnotation.correo();
	}
	
	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		try {
			
			
			final Object usuario = BeanUtils.getProperty(value, firstFieldName);
			final Object correo = BeanUtils.getProperty(value, secondFieldName);  

			
		} catch (final Exception ignore) {
			// ignore
		}
		return false;
	}
}