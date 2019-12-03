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
	public Respuesta<Boolean> registro(String correo, String contenido, UsuarioPublico usuario, String urlToSend) { 
		try {
			
			Mail mail = new Mail();
			mail.setFrom(stegeriluminacionCorreo);
			mail.setTo(correo);
			mail.setSubject(contenido);

			/* PROCESO PARA VALIDAR SU USUARIO */
			ResetTokenPublico token = new ResetTokenPublico();
			token.setToken(usuario.getUsername() + UUID.randomUUID().toString());
			token.setUsuarioPublico(usuario);
			token.setExpiracionInMinutes(1440);
			resetTokenPublicoDao.save(token);

			Map<String, Object> model = new HashMap<>();
			String url = urlToSend + token.getToken();
			model.put("user", token.getUsuarioPublico().getUsername());
			model.put("resetUrl", url);
			mail.setModel(model);
			
			Respuesta<Boolean> respuesta = iEmailExternalService.registro(mail); 
			 
			return respuesta;
			
		}catch( Exception ex) {
		    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}
    
    

    @Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,  readOnly = false)	
	@Override
	public Respuesta<Boolean> cambiarPassword(String correo, String contenido, UsuarioPublico usuario, String passwordTemp) { 
		try {
			
			Mail mail = new Mail();
			mail.setFrom(soporteCorreo);
			mail.setTo(correo);
			mail.setSubject(contenido);

			/* PROCESO PARA VALIDAR SU USUARIO */
			//ResetTokenPublico token = new ResetTokenPublico();
			//token.setToken(usuario.getUsername() + UUID.randomUUID().toString());
			//token.setUsuario(usuario);
			//token.setExpiracionInMinutes(2880);
			//resetTokenPublicoDao.save(token);

			Map<String, Object> model = new HashMap<>(); 
			model.put("user", usuario.getUsername());
			model.put("passwordTemp", passwordTemp);
			mail.setModel(model);
			
			Respuesta<Boolean> respuesta = iEmailExternalService.cambiarPassword(mail); 
			return respuesta;
			
		}catch( Exception ex) {
		    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}
    
    
    

}
