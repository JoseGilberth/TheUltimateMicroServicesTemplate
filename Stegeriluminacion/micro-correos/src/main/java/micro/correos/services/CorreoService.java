package micro.correos.services;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import dto.micro.correo.Mail;
import micro.correos.services.interfaces.ICorreoService;
 
@Service
public class CorreoService implements ICorreoService {

	Logger logger = LoggerFactory.getLogger(CorreoService.class);

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Async
	public void registro(Mail mail) { 
		try {

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name()); 
			
			Context context = new Context();  
			context.setVariables(mail.getModel());    
			context.setVariable("imageResourceName", "logo.png"); // so that we can reference it from HTML
			System.out.println("entro aqui <-------------->");
			
			String html = templateEngine.process("email/registro", context);
			helper.setTo(mail.getTo());
			helper.setText(html, true);
			helper.setSubject(mail.getSubject());
			helper.setFrom(mail.getFrom()); 
	        helper.addInline("logo.png", new ClassPathResource("/templates/email/images/logo.png") , "image/png");// SIEMPRE VA DESPUES
			emailSender.send(message);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	

	@Async
	public void cambiarContraseña(Mail mail) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

			Context context = new Context();
			context.setVariables(mail.getModel());
			context.setVariable("imageResourceName", "logo.png"); // so that we can reference it from HTML
			
			String html = templateEngine.process("email/cambio_password", context);
			helper.setTo(mail.getTo());
			helper.setText(html, true);
			helper.setSubject(mail.getSubject());
			helper.setFrom(mail.getFrom()); 
	        helper.addInline("logo.png", new ClassPathResource("/templates/email/images/logo.png") , "image/png");// SIEMPRE VA DESPUS
	        
			emailSender.send(message);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	

}
