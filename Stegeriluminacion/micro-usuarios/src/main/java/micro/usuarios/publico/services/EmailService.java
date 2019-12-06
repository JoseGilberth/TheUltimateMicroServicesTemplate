package micro.usuarios.publico.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import dao.auth.usuarios.publicos.ResetTokenPublicoDao;
import dto.main.Respuesta;
import dto.micro.correo.Mail;
import micro.usuarios.publico.services.externo.IEmailExternalService;
import micro.usuarios.publico.services.interfaces.IEmailService;
import modelo.auth.usuarios.publicos.ResetTokenPublico;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import  steger.excepciones.controladas.ErrorInternoControlado;

@Service
public class EmailService implements IEmailService {

	@Autowired
	ResetTokenPublicoDao resetTokenPublicoDao;

	@Autowired
	IEmailExternalService iEmailExternalService;
   
    @Value("${correo.stegeriluminacion}")
	String stegeriluminacionCorreo;

    @Value("${correo.soporte}")
	String soporteCorreo;

    @Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,  readOnly = false)	
	@Override
	public Respuesta<Boolean> registro(String[] correos, String[] bcc,String[] cc, String subject, UsuarioPublico usuario, String urlToSend) { 
		try {
			
			Mail mail = new Mail();
			mail.setFrom(stegeriluminacionCorreo);
			mail.setTo(correos);
			mail.setSubject(subject);

			/* PROCESO PARA VALIDAR SU USUARIO */
			ResetTokenPublico token = new ResetTokenPublico();
			token.setToken(usuario.getUsername() + UUID.randomUUID().toString());
			token.setUsuarioPublico(usuario);
			token.setExpiracionInMinutes(1440);
			resetTokenPublicoDao.save(token);

			Map<String, Object> variables = new HashMap<>();
			String url = urlToSend + token.getToken();
			variables.put("user", token.getUsuarioPublico().getUsername());
			variables.put("resetUrl", url);
			mail.setVariables(variables);
			
			Respuesta<Boolean> respuesta = iEmailExternalService.registro(mail); 
			
			//respuesta.setCodigo(200);
			//respuesta.setCodigoHttp(200);
			//respuesta.setCuerpo(true);
			//respuesta.setEstado(true);
			//respuesta.setMensaje(Translator.toLocale("usuarios.creado"));
			System.out.println(" entro aqui <---------------->");
			return respuesta;
			
		}catch( Exception ex) {
		    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}
    
    

    @Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,  readOnly = false)	
	@Override
	public Respuesta<Boolean> cambiarPassword(String[] correos, String[] bcc,String[] cc, String subject, UsuarioPublico usuario, String passwordTemp) { 
		try {
			
			Mail mail = new Mail();
			mail.setFrom(soporteCorreo);
			mail.setTo( correos );
			mail.setBcc(bcc);
			mail.setCc(cc); 
			mail.setSubject(subject);

			/* PROCESO PARA VALIDAR SU USUARIO */
			//ResetTokenPublico token = new ResetTokenPublico();
			//token.setToken(usuario.getUsername() + UUID.randomUUID().toString());
			//token.setUsuario(usuario);
			//token.setExpiracionInMinutes(2880);
			//resetTokenPublicoDao.save(token);

			Map<String, Object> variables = new HashMap<String, Object>(); 
			variables.put("user", usuario.getUsername());
			variables.put("passwordTemp", passwordTemp);
			mail.setVariables(variables);
			
			Respuesta<Boolean> respuesta = iEmailExternalService.cambiarPassword(mail); 
			return respuesta;
			
		}catch( Exception ex) {
		    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}
    
    
    

}
