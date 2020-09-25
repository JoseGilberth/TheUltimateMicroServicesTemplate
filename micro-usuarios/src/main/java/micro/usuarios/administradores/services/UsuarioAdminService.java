package micro.usuarios.administradores.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dao.auth.fingerprint.FingerPrintAuthenticationDao;
import dao.auth.fingerprint.FingerPrintFmdIndiceDerechoDao;
import dao.auth.fingerprint.FingerPrintFmdIndiceIzquierdoDao;
import dao.auth.fingerprint.FingerPrintFmdMedioDerechoDao;
import dao.auth.fingerprint.FingerPrintFmdMedioIzquierdoDao;
import dao.auth.fingerprint.FingerPrintFmdPulgarDerechoDao;
import dao.auth.fingerprint.FingerPrintFmdPulgarIzquierdoDao;
import dao.auth.usuarios.administradores.UsuarioAdministradorDao;
import dto.main.Respuesta;
import dto.usuarios.FiltroUsuarioAdminDTO;
import excepciones.controladas.ErrorInternoControlado;
import interfaces.interfaces.MainCrud;
import micro.usuarios.administradores.interfaces.IUsuarioAdminService;
import model.auth.usuarios.administradores.UsuarioAdministrador;
import model.auth.usuarios.fingerprint.FingerPrintAuthentication;
import model.auth.usuarios.fingerprint.FingerPrintFmdIndiceDerecho;
import model.auth.usuarios.fingerprint.FingerPrintFmdIndiceIzquierdo;
import model.auth.usuarios.fingerprint.FingerPrintFmdMedioDerecho;
import model.auth.usuarios.fingerprint.FingerPrintFmdMedioIzquierdo;
import model.auth.usuarios.fingerprint.FingerPrintFmdPulgarDerecho;
import model.auth.usuarios.fingerprint.FingerPrintFmdPulgarIzquierdo;
import utils._config.language.Translator;

@Service
public class UsuarioAdminService extends MainCrud<UsuarioAdministrador, Long> implements IUsuarioAdminService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UsuarioAdministradorDao usuarioAdministradorDao;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	FingerPrintAuthenticationDao fingerPrintAuthenticationDao;

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

	
	@Override
	public Respuesta<Page<UsuarioAdministrador>> filtrar(Pageable pageable,
			FiltroUsuarioAdminDTO filtroUsuarioAdminDTO) {
		Page<UsuarioAdministrador> datos = usuarioAdministradorDao.filtro(pageable, filtroUsuarioAdminDTO);
		return new Respuesta<Page<UsuarioAdministrador>>(200, datos,
				Translator.toLocale("usuarios.admin.usuarioadminservice.filtrar"));
	}

	@Override
	public Respuesta<UsuarioAdministrador> crear(UsuarioAdministrador usuarioNuevo) {

		UsuarioAdministrador usuario = usuarioAdministradorDao.findByUsername(usuarioNuevo.getUsername());
		if (usuario != null) {
			return ErrorInternoControlado.usuarioOCorreoDuplicado(usuarioNuevo.getUsername());
		}
		usuarioNuevo.setPassword(bcrypt.encode(usuarioNuevo.getPassword()));
		UsuarioAdministrador usuarioAdmin = usuarioAdministradorDao.saveAndFlush(usuarioNuevo);
		return new Respuesta<UsuarioAdministrador>(200, usuarioAdmin,
				Translator.toLocale("usuarios.admin.usuarioadminservice.crear"));
	}

	@Override
	public Respuesta<UsuarioAdministrador> actualizar(Long id, UsuarioAdministrador usuario) {
		UsuarioAdministrador usuarioActual = usuarioAdministradorDao.findById(id).get();

		/* PASSWORD */
		if (usuario.getPassword().isEmpty()) {
			usuario.setPassword(usuarioActual.getPassword());
		} else {
			PasswordValidator validator = new PasswordValidator(Arrays.asList(
					// at least 8 characters
					new LengthRule(5, 30),
					// at least one upper-case character
					new CharacterRule(EnglishCharacterData.UpperCase, 1),
					// at least one lower-case character
					new CharacterRule(EnglishCharacterData.LowerCase, 1),
					// at least one digit character
					new CharacterRule(EnglishCharacterData.Digit, 1),
					// at least one symbol (special character)
					new CharacterRule(EnglishCharacterData.Special, 1),
					// no whitespace
					new WhitespaceRule()));
			RuleResult result = validator.validate(new PasswordData(usuario.getPassword()));
			if (result.isValid()) {
				usuario.setPassword(bcrypt.encode(usuario.getPassword()));
			} else {
				List<String> messages = validator.getMessages(result);
				String messageTemplate = messages.stream().collect(Collectors.joining(","));
				return ErrorInternoControlado.passwordNoValido(messageTemplate);
			}
		}
		updateFingerPrints(usuario, usuarioActual);
		UsuarioAdministrador usuarioAdmin = usuarioAdministradorDao.saveAndFlush(usuario);
		return new Respuesta<UsuarioAdministrador>(200, usuarioAdmin,
				Translator.toLocale("usuarios.admin.usuarioadminservice.actualizar"));
	}

	@Override
	public Respuesta<Boolean> actualizarHuellas(FingerPrintAuthentication fingerPrintAuthentication) {
		UsuarioAdministrador usuarioActual = usuarioAdministradorDao
				.findByUsername(fingerPrintAuthentication.getUsuario());
		if (usuarioActual == null) {
			return new Respuesta<Boolean>(500, false,
					Translator.toLocale("usuarios.admin.usuarioadminservice.actualizarHuellas.usuarionoexiste"));
		}

		enrollFingerPrints(usuarioActual, fingerPrintAuthentication);
		usuarioAdministradorDao.saveAndFlush(usuarioActual);

		return new Respuesta<Boolean>(200, true,
				Translator.toLocale("usuarios.admin.usuarioadminservice.actualizarHuellas"));
	}

	private void enrollFingerPrints(UsuarioAdministrador usuarioActual,
			FingerPrintAuthentication fingerPrintAuthentication) {

		if (usuarioActual.getFingerPrintAuthentication() != null) {
			fingerPrintAuthentication.setId(usuarioActual.getFingerPrintAuthentication().getId());
		}

		for (FingerPrintFmdIndiceDerecho fingerPrintFmdIndiceDerecho : fingerPrintAuthentication
				.getFingerPrintFmdIndiceDerecho()) {
			fingerPrintFmdIndiceDerecho.setFingerPrintAuthentication(fingerPrintAuthentication);
		}
		for (FingerPrintFmdMedioDerecho fingerPrintFmdMedioDerecho : fingerPrintAuthentication
				.getFingerPrintFmdMedioDerecho()) {
			fingerPrintFmdMedioDerecho.setFingerPrintAuthentication(fingerPrintAuthentication);
		}
		for (FingerPrintFmdPulgarDerecho fingerPrintFmdPulgarDerecho : fingerPrintAuthentication
				.getFingerPrintFmdPulgarDerecho()) {
			fingerPrintFmdPulgarDerecho.setFingerPrintAuthentication(fingerPrintAuthentication);
		}
		for (FingerPrintFmdIndiceIzquierdo fingerPrintFmdIndiceIzquierdo : fingerPrintAuthentication
				.getFingerPrintFmdIndiceIzquierdo()) {
			fingerPrintFmdIndiceIzquierdo.setFingerPrintAuthentication(fingerPrintAuthentication);
		}
		for (FingerPrintFmdMedioIzquierdo fingerPrintFmdMedioIzquierdo : fingerPrintAuthentication
				.getFingerPrintFmdMedioIzquierdo()) {
			fingerPrintFmdMedioIzquierdo.setFingerPrintAuthentication(fingerPrintAuthentication);
		}
		for (FingerPrintFmdPulgarIzquierdo fingerPrintFmdPulgarIzquierdo : fingerPrintAuthentication
				.getFingerPrintFmdPulgarIzquierdo()) {
			fingerPrintFmdPulgarIzquierdo.setFingerPrintAuthentication(fingerPrintAuthentication);
		}
		usuarioActual.setFingerPrintAuthentication(fingerPrintAuthentication);
	}

	private void updateFingerPrints(UsuarioAdministrador usuario, UsuarioAdministrador usuarioActual) {

		if (usuario.getFingerPrintAuthentication() != null) {
			if (usuarioActual.getFingerPrintAuthentication() != null) {

				usuario.getFingerPrintAuthentication().setId(usuarioActual.getFingerPrintAuthentication().getId());

				List<FingerPrintFmdIndiceDerecho> fingersPrintFmdIndiceDerecho = fingerPrintFmdIndiceDerechoDao
						.findByFingerPrintAuthentication(usuarioActual.getFingerPrintAuthentication().getId());
				List<FingerPrintFmdIndiceIzquierdo> fingersPrintFmdIndiceIzquierdo = fingerPrintFmdIndiceIzquierdoDao
						.findByFingerPrintAuthentication(usuarioActual.getFingerPrintAuthentication().getId());
				List<FingerPrintFmdMedioDerecho> fingersPrintFmdMedioDerecho = fingerPrintFmdMedioDerechoDao
						.findByFingerPrintAuthentication(usuarioActual.getFingerPrintAuthentication().getId());
				List<FingerPrintFmdMedioIzquierdo> fingersPrintFmdMedioIzquierdo = fingerPrintFmdMedioIzquierdoDao
						.findByFingerPrintAuthentication(usuarioActual.getFingerPrintAuthentication().getId());
				List<FingerPrintFmdPulgarDerecho> fingersPrintFmdPulgarDerecho = fingerPrintFmdPulgarDerechoDao
						.findByFingerPrintAuthentication(usuarioActual.getFingerPrintAuthentication().getId());
				List<FingerPrintFmdPulgarIzquierdo> fingersPrintFmdPulgarIzquierdo = fingerPrintFmdPulgarIzquierdoDao
						.findByFingerPrintAuthentication(usuarioActual.getFingerPrintAuthentication().getId());

				if (usuario.getFingerPrintAuthentication().getFingerPrintFmdIndiceDerecho().size() > 0) {
					fingerPrintFmdIndiceDerechoDao.deleteAll(fingersPrintFmdIndiceDerecho);
				} else {
					usuario.getFingerPrintAuthentication().setFingerPrintFmdIndiceDerecho(
							new HashSet<FingerPrintFmdIndiceDerecho>(fingersPrintFmdIndiceDerecho));
				}

				if (usuario.getFingerPrintAuthentication().getFingerPrintFmdMedioDerecho().size() > 0) {
					fingerPrintFmdMedioDerechoDao.deleteAll(fingersPrintFmdMedioDerecho);
				} else {
					usuario.getFingerPrintAuthentication().setFingerPrintFmdMedioDerecho(
							new HashSet<FingerPrintFmdMedioDerecho>(fingersPrintFmdMedioDerecho));
				}

				if (usuario.getFingerPrintAuthentication().getFingerPrintFmdPulgarDerecho().size() > 0) {
					fingerPrintFmdPulgarDerechoDao.deleteAll(fingersPrintFmdPulgarDerecho);
				} else {
					usuario.getFingerPrintAuthentication().setFingerPrintFmdPulgarDerecho(
							new HashSet<FingerPrintFmdPulgarDerecho>(fingersPrintFmdPulgarDerecho));
				}

				if (usuario.getFingerPrintAuthentication().getFingerPrintFmdIndiceIzquierdo().size() > 0) {
					fingerPrintFmdIndiceIzquierdoDao.deleteAll(fingersPrintFmdIndiceIzquierdo);
				} else {
					usuario.getFingerPrintAuthentication().setFingerPrintFmdIndiceIzquierdo(
							new HashSet<FingerPrintFmdIndiceIzquierdo>(fingersPrintFmdIndiceIzquierdo));
				}

				if (usuario.getFingerPrintAuthentication().getFingerPrintFmdMedioIzquierdo().size() > 0) {
					fingerPrintFmdMedioIzquierdoDao.deleteAll(fingersPrintFmdMedioIzquierdo);
				} else {
					usuario.getFingerPrintAuthentication().setFingerPrintFmdMedioIzquierdo(
							new HashSet<FingerPrintFmdMedioIzquierdo>(fingersPrintFmdMedioIzquierdo));
				}

				if (usuario.getFingerPrintAuthentication().getFingerPrintFmdPulgarIzquierdo().size() > 0) {
					fingerPrintFmdPulgarIzquierdoDao.deleteAll(fingersPrintFmdPulgarIzquierdo);
				} else {
					usuario.getFingerPrintAuthentication().setFingerPrintFmdPulgarIzquierdo(
							new HashSet<FingerPrintFmdPulgarIzquierdo>(fingersPrintFmdPulgarIzquierdo));
				}

				for (FingerPrintFmdIndiceDerecho fingerPrintFmdIndiceDerecho : usuario.getFingerPrintAuthentication()
						.getFingerPrintFmdIndiceDerecho()) {
					fingerPrintFmdIndiceDerecho
							.setFingerPrintAuthentication(usuarioActual.getFingerPrintAuthentication());
				}
				for (FingerPrintFmdMedioDerecho fingerPrintFmdMedioDerecho : usuario.getFingerPrintAuthentication()
						.getFingerPrintFmdMedioDerecho()) {
					fingerPrintFmdMedioDerecho
							.setFingerPrintAuthentication(usuarioActual.getFingerPrintAuthentication());
				}
				for (FingerPrintFmdPulgarDerecho fingerPrintFmdPulgarDerecho : usuario.getFingerPrintAuthentication()
						.getFingerPrintFmdPulgarDerecho()) {
					fingerPrintFmdPulgarDerecho
							.setFingerPrintAuthentication(usuarioActual.getFingerPrintAuthentication());
				}
				for (FingerPrintFmdIndiceIzquierdo fingerPrintFmdIndiceIzquierdo : usuario
						.getFingerPrintAuthentication().getFingerPrintFmdIndiceIzquierdo()) {
					fingerPrintFmdIndiceIzquierdo
							.setFingerPrintAuthentication(usuarioActual.getFingerPrintAuthentication());
				}
				for (FingerPrintFmdMedioIzquierdo fingerPrintFmdMedioIzquierdo : usuario.getFingerPrintAuthentication()
						.getFingerPrintFmdMedioIzquierdo()) {
					fingerPrintFmdMedioIzquierdo
							.setFingerPrintAuthentication(usuarioActual.getFingerPrintAuthentication());
				}
				for (FingerPrintFmdPulgarIzquierdo fingerPrintFmdPulgarIzquierdo : usuario
						.getFingerPrintAuthentication().getFingerPrintFmdPulgarIzquierdo()) {
					fingerPrintFmdPulgarIzquierdo
							.setFingerPrintAuthentication(usuarioActual.getFingerPrintAuthentication());
				}
			} else {
				for (FingerPrintFmdIndiceDerecho fingerPrintFmdIndiceDerecho : usuario.getFingerPrintAuthentication()
						.getFingerPrintFmdIndiceDerecho()) {
					fingerPrintFmdIndiceDerecho.setFingerPrintAuthentication(usuario.getFingerPrintAuthentication());
				}
				for (FingerPrintFmdMedioDerecho fingerPrintFmdMedioDerecho : usuario.getFingerPrintAuthentication()
						.getFingerPrintFmdMedioDerecho()) {
					fingerPrintFmdMedioDerecho.setFingerPrintAuthentication(usuario.getFingerPrintAuthentication());
				}
				for (FingerPrintFmdPulgarDerecho fingerPrintFmdPulgarDerecho : usuario.getFingerPrintAuthentication()
						.getFingerPrintFmdPulgarDerecho()) {
					fingerPrintFmdPulgarDerecho.setFingerPrintAuthentication(usuario.getFingerPrintAuthentication());
				}
				for (FingerPrintFmdIndiceIzquierdo fingerPrintFmdIndiceIzquierdo : usuario
						.getFingerPrintAuthentication().getFingerPrintFmdIndiceIzquierdo()) {
					fingerPrintFmdIndiceIzquierdo.setFingerPrintAuthentication(usuario.getFingerPrintAuthentication());
				}
				for (FingerPrintFmdMedioIzquierdo fingerPrintFmdMedioIzquierdo : usuario.getFingerPrintAuthentication()
						.getFingerPrintFmdMedioIzquierdo()) {
					fingerPrintFmdMedioIzquierdo.setFingerPrintAuthentication(usuario.getFingerPrintAuthentication());
				}
				for (FingerPrintFmdPulgarIzquierdo fingerPrintFmdPulgarIzquierdo : usuario
						.getFingerPrintAuthentication().getFingerPrintFmdPulgarIzquierdo()) {
					fingerPrintFmdPulgarIzquierdo.setFingerPrintAuthentication(usuario.getFingerPrintAuthentication());
				}
			}
		}
	}

}
