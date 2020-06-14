package micro.correos.services;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import dto.main.Respuesta;
import dto.micro.correo.Mail;
import micro.correos._config.languaje.Translator;
import micro.correos.services.interfaces.IEmailService;

@Component
public class SendEmailService implements IEmailService {
	
	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;
  
	@Override
	public Respuesta<Boolean> sendEmail(Mail mail, String template) throws MessagingException { 
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>(200, 200, true, Translator.toLocale("correo.enviado"), true); 
		
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
		
		// CONTEXTO
		Context context = new Context();
		context.setVariables(mail.getVariables());
		context.setVariable("imageResourceName", "logo.png");
		String html = templateEngine.process(template, context);

		// PROPERTIES
		helper.setFrom(mail.getFrom());
		helper.setSubject(mail.getSubject());
		helper.setTo(mail.getTo());
		helper.setBcc( mail.getBcc() );
		helper.setCc(mail.getCc());
		helper.setPriority(mail.getPriority());
		helper.setReplyTo(mail.getReplyTo());
		helper.setSentDate(mail.getSentDate());
		helper.setText(html, true);
		
		// INLINE FILES
		helper.addInline("logo.png", new ClassPathResource("/templates/email/images/logo.png"), "image/png");
		
		emailSender.send(message);
		
		return respuesta;
	}

}
