package micro.auth._config.security.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;

import dao.auth.fingerprint.FingerPrintFmdIndiceDerechoDao;
import dao.auth.fingerprint.FingerPrintFmdIndiceIzquierdoDao;
import dao.auth.fingerprint.FingerPrintFmdMedioDerechoDao;
import dao.auth.fingerprint.FingerPrintFmdMedioIzquierdoDao;
import dao.auth.fingerprint.FingerPrintFmdPulgarDerechoDao;
import dao.auth.fingerprint.FingerPrintFmdPulgarIzquierdoDao;
import dao.auth.usuarios.administradores.UsuarioAdministradorDao;
import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import dto.main.MessageWebsocket;
import dto.main.Respuesta;
import micro.auth.services.externos.websocket.interfaces.IWebSocketService;
import model.auth.usuarios.administradores.UsuarioAdministrador;
import model.auth.usuarios.fingerprint.FingerPrintFmdIndiceDerecho;
import model.auth.usuarios.fingerprint.FingerPrintFmdIndiceIzquierdo;
import model.auth.usuarios.fingerprint.FingerPrintFmdMedioDerecho;
import model.auth.usuarios.fingerprint.FingerPrintFmdMedioIzquierdo;
import model.auth.usuarios.fingerprint.FingerPrintFmdPulgarDerecho;
import model.auth.usuarios.fingerprint.FingerPrintFmdPulgarIzquierdo;
import model.auth.usuarios.publicos.UsuarioPublico;
import utils.Converters;
import utils.StaticVariables;
import utils._config.language.Translator;

public class CustomDaoAuthenticationProvider implements AuthenticationProvider {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UsuarioAdministradorDao usuarioAdministradorDao;

	@Autowired
	public UsuarioPublicoDao usuarioPublicoDao;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	FingerPrintFmdIndiceDerechoDao fingerPrintFmdIndiceDerechoDao;

	@Autowired
	FingerPrintFmdIndiceIzquierdoDao fingerPrintFmdIndiceIzquierdoDao;

	@Autowired
	FingerPrintFmdMedioDerechoDao fingerPrintFmdMedioDerechoDao;

	@Autowired
	FingerPrintFmdMedioIzquierdoDao fingerPrintFmdMedioIzquierdoDao;

	@Autowired
	FingerPrintFmdPulgarDerechoDao fingerPrintFmdPulgarDerechoDao;

	@Autowired
	FingerPrintFmdPulgarIzquierdoDao fingerPrintFmdPulgarIzquierdoDao;

	@Autowired
	IWebSocketService iWebSocketService;

	@Autowired(required = false)
	private HttpServletRequest request;

	@Value("${websocket.topics.admin}")
	private String topicAdmin;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		String huella = null;
		try {
			huella = request.getParameter(StaticVariables.HUELLA);
		} catch (Exception ex) {
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		UsuarioAdministrador usuarioAdministrador = usuarioAdministradorDao.findByUsername(username);
		UsuarioPublico usuarioPublico = usuarioPublicoDao.findByUsernameOrEmail(username, username);

		if (usuarioPublico == null && usuarioAdministrador == null) {
			throw new UsernameNotFoundException(String.format(Translator.toLocale("login.usuario.noexiste"), username));
		}

		if (usuarioPublico != null && usuarioAdministrador != null) {
			throw new SessionAuthenticationException(
					String.format(Translator.toLocale("login.usuario.same.users"), username));
		}

		// VALIDACION DE USUARIOS ADMINISTRADORES
		if (usuarioAdministrador != null) {
			if (!usuarioAdministrador.isEnabled()) {
				throw new DisabledException(Translator.toLocale("login.usuario.deshabilitado"));
			}
			if (huella != null) {
				if (usuarioAdministrador.getFingerPrintAuthentication() != null) {
					if (!fingerPrintLogin(huella, usuarioAdministrador.getFingerPrintAuthentication().getId())) {
						throw new BadCredentialsException(Translator.toLocale("login.huella.incorrecta"));
					}
				} else {
					throw new BadCredentialsException(Translator.toLocale("login.huella.noenrollada"));
				}
			} else {
				if (!bcrypt.matches(password, usuarioAdministrador.getPassword())) {
					throw new BadCredentialsException(Translator.toLocale("login.password.incorrecto"));
				}
			}
			usuarioAdministrador.getPermisos().forEach(permiso -> {
				authorities.add(new SimpleGrantedAuthority(permiso.getEtiqueta()));
			});
		}

		// VALIDACION DE USUARIOS PUBLICOS
		if (usuarioPublico != null) {
			if (!usuarioPublico.isEnabled()) {
				throw new DisabledException(Translator.toLocale("login.usuario.publico.deshabilitado"));
			}
			if (huella != null) {
				if (usuarioPublico.getFingerPrintAuthentication() != null) {
					if (!fingerPrintLogin(huella, usuarioPublico.getFingerPrintAuthentication().getId())) {
						throw new BadCredentialsException(Translator.toLocale("login.huella.incorrecta"));
					}
				} else {
					throw new BadCredentialsException(Translator.toLocale("login.huella.noenrollada"));
				}
			} else {
				if (!bcrypt.matches(password, usuarioPublico.getPassword())) {
					throw new BadCredentialsException(Translator.toLocale("login.password.incorrecto"));
				}
			}
			usuarioPublico.getPermisos().forEach(permiso -> {
				authorities.add(new SimpleGrantedAuthority(permiso.getEtiqueta()));
			});
		}

		try {
			Respuesta<Boolean> respuesta = iWebSocketService.sendMessage(new MessageWebsocket(username,
					Translator.toLocale("notification.login.init.session", new String[] { username }), 
					"Login", topicAdmin));
			logger.info("respuesta iWebSocketService: " + respuesta.getCodigoHttp());
			logger.info("respuesta iWebSocketService: " + respuesta.getMensaje());
			logger.info("respuesta iWebSocketService: " + respuesta.getCuerpo());
		} catch (Exception ex) {
			logger.error("iWebSocketService: " + ex.getMessage());
		}

		Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);
		return auth;
	}

	public boolean fingerPrintLogin(String huella, Long idAuthentication) {
		try {

			Engine engine = UareUGlobal.GetEngine();
			Fmd fmdToIdentify = Converters.importImageString(huella);

			List<FingerPrintFmdIndiceDerecho> fingersPrintFmdIndiceDerecho = fingerPrintFmdIndiceDerechoDao
					.findByFingerPrintAuthentication(idAuthentication);
			List<FingerPrintFmdIndiceIzquierdo> fingersPrintFmdIndiceIzquierdo = fingerPrintFmdIndiceIzquierdoDao
					.findByFingerPrintAuthentication(idAuthentication);
			List<FingerPrintFmdMedioDerecho> fingersPrintFmdMedioDerecho = fingerPrintFmdMedioDerechoDao
					.findByFingerPrintAuthentication(idAuthentication);
			List<FingerPrintFmdMedioIzquierdo> fingersPrintFmdMedioIzquierdo = fingerPrintFmdMedioIzquierdoDao
					.findByFingerPrintAuthentication(idAuthentication);
			List<FingerPrintFmdPulgarDerecho> fingersPrintFmdPulgarDerecho = fingerPrintFmdPulgarDerechoDao
					.findByFingerPrintAuthentication(idAuthentication);
			List<FingerPrintFmdPulgarIzquierdo> fingersPrintFmdPulgarIzquierdo = fingerPrintFmdPulgarIzquierdoDao
					.findByFingerPrintAuthentication(idAuthentication);

			List<byte[]> listHuellas = new ArrayList<byte[]>();
			listHuellas.addAll(fingersPrintFmdIndiceDerecho.stream().map(FingerPrintFmdIndiceDerecho::getArchivo)
					.collect(Collectors.toList()));
			listHuellas.addAll(fingersPrintFmdIndiceIzquierdo.stream().map(FingerPrintFmdIndiceIzquierdo::getArchivo)
					.collect(Collectors.toList()));
			listHuellas.addAll(fingersPrintFmdMedioDerecho.stream().map(FingerPrintFmdMedioDerecho::getArchivo)
					.collect(Collectors.toList()));
			listHuellas.addAll(fingersPrintFmdMedioIzquierdo.stream().map(FingerPrintFmdMedioIzquierdo::getArchivo)
					.collect(Collectors.toList()));
			listHuellas.addAll(fingersPrintFmdPulgarDerecho.stream().map(FingerPrintFmdPulgarDerecho::getArchivo)
					.collect(Collectors.toList()));
			listHuellas.addAll(fingersPrintFmdPulgarIzquierdo.stream().map(FingerPrintFmdPulgarIzquierdo::getArchivo)
					.collect(Collectors.toList()));

			if (listHuellas.size() > 0) {
				Fmd[] m_fmds = new Fmd[listHuellas.size()];
				try {
					for (int i = 0; i < listHuellas.size(); i++) {
						m_fmds[i] = Converters.importImageBytes(listHuellas.get(i));
					}

					int falsepositive_rate = Engine.PROBABILITY_ONE / 100000;
					Engine.Candidate[] vCandidates = engine.Identify(fmdToIdentify, 0, m_fmds, falsepositive_rate,
							m_fmds.length);
					if (0 != vCandidates.length) {
						int falsematch_rate = engine.Compare(fmdToIdentify, 0, m_fmds[vCandidates[0].fmd_index],
								vCandidates[0].view_index);
						if (falsematch_rate < falsepositive_rate) {
							logger.info("Fingerprints matched.");
							String str = String.format("dissimilarity score: 0x%x.\n", falsematch_rate);
							logger.info(str);
							str = String.format("false match rate: %e.\n\n\n",
									(double) (falsematch_rate / Engine.PROBABILITY_ONE));
							logger.info(str);
							return true;
						}
					}
				} catch (UareUException e1) {
					e1.printStackTrace();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
