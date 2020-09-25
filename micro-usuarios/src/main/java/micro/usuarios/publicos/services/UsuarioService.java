package micro.usuarios.publicos.services;

import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import dao.auth.fingerprint.FingerPrintAuthenticationDao;
import dao.auth.fingerprint.FingerPrintFmdIndiceDerechoDao;
import dao.auth.fingerprint.FingerPrintFmdIndiceIzquierdoDao;
import dao.auth.fingerprint.FingerPrintFmdMedioDerechoDao;
import dao.auth.fingerprint.FingerPrintFmdMedioIzquierdoDao;
import dao.auth.fingerprint.FingerPrintFmdPulgarDerechoDao;
import dao.auth.fingerprint.FingerPrintFmdPulgarIzquierdoDao;
import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import dto.main.Respuesta;
import dto.usuarios.FiltroUsuarioPublicoDTO;
import dto.usuarios.PatchUsuarioPublicoDTO;
import excepciones.controladas.ErrorInternoControlado;
import interfaces.interfaces.MainCrud;
import micro.usuarios.publicos.interfaces.IUsuarioService;
import model.auth.usuarios.fingerprint.FingerPrintAuthentication;
import model.auth.usuarios.fingerprint.FingerPrintFmdIndiceDerecho;
import model.auth.usuarios.fingerprint.FingerPrintFmdIndiceIzquierdo;
import model.auth.usuarios.fingerprint.FingerPrintFmdMedioDerecho;
import model.auth.usuarios.fingerprint.FingerPrintFmdMedioIzquierdo;
import model.auth.usuarios.fingerprint.FingerPrintFmdPulgarDerecho;
import model.auth.usuarios.fingerprint.FingerPrintFmdPulgarIzquierdo;
import model.auth.usuarios.publicos.UsuarioPublico;
import utils._config.language.Translator;

@Service
public class UsuarioService extends MainCrud<UsuarioPublico, Long> implements IUsuarioService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UsuarioPublicoDao usuarioPublicoDao;

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
	public Respuesta<Page<UsuarioPublico>> filtrar(Pageable pageable, FiltroUsuarioPublicoDTO filtroUsuarioPublicoDTO) {
		Page<UsuarioPublico> datos = usuarioPublicoDao.filtro(pageable, filtroUsuarioPublicoDTO);
		return new Respuesta<Page<UsuarioPublico>>(200, datos,
				Translator.toLocale("usuarios.publico.usuarioservice.filtrar"));
	}

	@Override
	public Respuesta<UsuarioPublico> obtenerPorToken(OAuth2Authentication auth) {
		UsuarioPublico usuarioPublico = usuarioPublicoDao.findByUsernameOrEmail(auth.getPrincipal().toString(),
				auth.getPrincipal().toString());
		return new Respuesta<UsuarioPublico>(200, usuarioPublico,
				Translator.toLocale("usuarios.publico.usuarioservice.obtenerportoken"));
	}

	@Override
	public Respuesta<UsuarioPublico> crear(UsuarioPublico usuarioPublico) {

		UsuarioPublico usuario = usuarioPublicoDao.findByUsernameOrEmail(usuarioPublico.getUsername(),
				usuarioPublico.getCorreo());
		if (usuario != null) {
			return ErrorInternoControlado.usuarioOCorreoDuplicado(usuarioPublico.getUsername());
		}

		usuarioPublico.setPassword(bcrypt.encode(usuarioPublico.getPassword()));
		UsuarioPublico usuarioPublic = usuarioPublicoDao.saveAndFlush(usuarioPublico);
		return new Respuesta<UsuarioPublico>(200, usuarioPublic,
				Translator.toLocale("usuarios.publico.usuarioservice.crear"));
	}

	// PATCH
	@Override
	public Respuesta<UsuarioPublico> actualizar(PatchUsuarioPublicoDTO patchUsuarioPublicoDTO,
			OAuth2Authentication auth) {

		UsuarioPublico usuarioActual = usuarioPublicoDao.findByUsernameOrEmail(auth.getPrincipal().toString(),
				auth.getPrincipal().toString());

		if (!patchUsuarioPublicoDTO.getPassword().equals(patchUsuarioPublicoDTO.getRepetirPassword())) {
			return ErrorInternoControlado.passwordsNoCoinciden();
		}
		if (patchUsuarioPublicoDTO.getPassword().isEmpty()) {
			patchUsuarioPublicoDTO.setPassword(usuarioActual.getPassword());
		} else {
			patchUsuarioPublicoDTO.setPassword(bcrypt.encode(patchUsuarioPublicoDTO.getPassword()));
		}

		BeanUtils.copyProperties(patchUsuarioPublicoDTO, usuarioActual);
		usuarioActual = usuarioPublicoDao.saveAndFlush(usuarioActual);

		return new Respuesta<UsuarioPublico>(200, usuarioActual,
				Translator.toLocale("usuarios.publico.usuarioservice.actualizar"));
	}

	@Override
	public Respuesta<UsuarioPublico> actualizar(Long id, UsuarioPublico usuarioPublico) {

		UsuarioPublico usuarioActual = usuarioPublicoDao.findById(id).get();

		/* PASSWORD */
		if (usuarioPublico.getPassword().isEmpty()) {
			usuarioPublico.setPassword(usuarioActual.getPassword());
		} else {
			usuarioPublico.setPassword(bcrypt.encode(usuarioPublico.getPassword()));
		}

		updateFingerPrints(usuarioPublico, usuarioActual);

		UsuarioPublico usuarioPublic = usuarioPublicoDao.saveAndFlush(usuarioPublico);

		return new Respuesta<UsuarioPublico>(200, usuarioPublic,
				Translator.toLocale("usuarios.publico.usuarioservice.actualizar"));
	}

	@Override
	public Respuesta<Boolean> borrar(Long id) {
		usuarioPublicoDao.deleteById(id);
		return new Respuesta<Boolean>(200, true, Translator.toLocale("usuarios.publico.usuarioservice.borrar"));
	}

	@Override
	public Respuesta<Boolean> actualizarHuellas(FingerPrintAuthentication fingerPrintAuthentication) {
		UsuarioPublico usuarioActual = usuarioPublicoDao.findByUsername(fingerPrintAuthentication.getUsuario());
		if (usuarioActual == null) {
			return new Respuesta<Boolean>(500, false,
					Translator.toLocale("usuarios.publico.usuarioservice.actualizarHuellas.usuarionoexiste"));
		}

		enrollFingerPrints(usuarioActual, fingerPrintAuthentication);
		usuarioPublicoDao.saveAndFlush(usuarioActual);

		return new Respuesta<Boolean>(200, true,
				Translator.toLocale("usuarios.publico.usuarioservice.actualizarHuellas"));
	}

	private void enrollFingerPrints(UsuarioPublico usuarioActual, FingerPrintAuthentication fingerPrintAuthentication) {

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

	private void updateFingerPrints(UsuarioPublico usuario, UsuarioPublico usuarioActual) {

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
