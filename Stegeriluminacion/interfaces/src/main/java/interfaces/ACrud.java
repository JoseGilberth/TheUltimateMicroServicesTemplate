package interfaces;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import dto.main.Respuesta;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import steger.excepciones.controladas.ErrorInternoControlado;

@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public abstract class ACrud< T extends Serializable, S> {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	private Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	Logger logger = LoggerFactory.getLogger(clazz);

	public Respuesta<T> obtener(final S id) {
		final Respuesta<T> respuesta = new Respuesta<T>();
		T objeto = entityManager.find(clazz, id);
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(objeto);
		respuesta.setEstado(true);
		respuesta.setMensaje("obtenido");
		return respuesta;
	}
	

	@SuppressWarnings("unchecked")
	public Respuesta<List<T>> obtenerTodos() {
		final Respuesta<List<T>> respuesta = new Respuesta<List<T>>();
		try {
			List<T> datos = entityManager.createQuery("from " + clazz.getName()).getResultList();
			respuesta.setCodigo(200);
			respuesta.setCodigoHttp(200);
			respuesta.setCuerpo(datos);
			respuesta.setEstado(true);
			respuesta.setMensaje("obtenidos");
			return respuesta;
		} catch (Exception ex) {
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}
	

	public Respuesta<T> crear(final T objeto) {
		final Respuesta<T> respuesta = new Respuesta<T>();
		entityManager.persist(objeto);
		entityManager.flush();
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(objeto);
		respuesta.setEstado(true);
		respuesta.setMensaje("creado");
		return respuesta;
	}

	
	public Respuesta<T> actualizar(final T objeto) {
		final Respuesta<T> respuesta = new Respuesta<T>();
		entityManager.merge(objeto);
		entityManager.flush();
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(objeto);
		respuesta.setEstado(true);
		respuesta.setMensaje("actualizado");
		return respuesta;
	}

	
	public Respuesta<Boolean> eliminar(final  S id) {
		final Respuesta<Boolean> respuesta = new Respuesta<Boolean>();
		T objeto = entityManager.find(clazz, id);
		entityManager.remove(objeto);
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(true);
		respuesta.setEstado(true);
		respuesta.setMensaje("eliminado");
		return respuesta;
	}

	@SuppressWarnings("unchecked")
	public Respuesta<T> obtenerPorUsuario(OAuth2Authentication auth, final S id) {
		final Respuesta<T> respuesta = new Respuesta<T>();

		for (Field field : clazz.getDeclaredFields()) {
			if (field.getType().equals(UsuarioPublico.class)) {
				T datos = (T) entityManager
						.createQuery("from " + clazz.getName() + " class " + "INNER JOIN class." + field.getName()
								+ " usuario " + " WHERE usuario.username =:usuario AND class.id =:idClass")
						.setParameter("usuario", auth.getPrincipal().toString()).setParameter("idClass", id)
						.getSingleResult();
				respuesta.setCodigo(200);
				respuesta.setCodigoHttp(200);
				respuesta.setCuerpo(datos);
				respuesta.setEstado(true);
				respuesta.setMensaje("obtenido");
				return respuesta;
			}
		}

		respuesta.setCodigo(400);
		respuesta.setCodigoHttp(400);
		respuesta.setCuerpo(null);
		respuesta.setEstado(true);
		respuesta.setMensaje("El modelo no cuenta con la estructura mapeada de UsuarioPublico");
		return respuesta;
	}

	@SuppressWarnings("unchecked")
	public Respuesta<List<T>> obtenerTodosPorUsuario(OAuth2Authentication auth) {
		final Respuesta<List<T>> respuesta = new Respuesta<List<T>>();
		try {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.getType().equals(UsuarioPublico.class)) {
					List<T> datos = entityManager
							.createQuery("from " + clazz.getName() + " class " + "INNER JOIN class." + field.getName()
									+ " usuario " + "WHERE usuario.username =:usuario ")
							.setParameter("usuario", auth.getPrincipal().toString()).getResultList();
					respuesta.setCodigo(200);
					respuesta.setCodigoHttp(200);
					respuesta.setCuerpo(datos);
					respuesta.setEstado(true);
					respuesta.setMensaje("obtenidos");
					return respuesta;
				}
			}
			respuesta.setCodigo(400);
			respuesta.setCodigoHttp(400);
			respuesta.setCuerpo(null);
			respuesta.setEstado(true);
			respuesta.setMensaje("El modelo no cuenta con la estructura mapeada de UsuarioPublico");
			return respuesta;
		} catch (Exception ex) {
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	public Respuesta<T> crearPorUsuario(OAuth2Authentication auth, final T objeto) {
		logger.info("CLASE ===>" + objeto.getClass().getName());
		String usuario = validaUsuario(objeto);

		final Respuesta<T> respuesta = new Respuesta<T>();
		respuesta.setCodigo(400);
		respuesta.setCodigoHttp(400);
		respuesta.setCuerpo(objeto);
		respuesta.setEstado(false);

		if (usuario == null) { // SI ENCONTRO EL USUARIO DENTRO DEL OBJETO
			respuesta.setMensaje("EL usuario no existe o no tiene mapeada la clase de usuario publico");
			return respuesta;
		}

		if (usuario.equals(auth.getPrincipal().toString())) { // SI SON LOS MISMOS
			entityManager.persist(objeto);
			entityManager.flush();
			respuesta.setCodigo(200);
			respuesta.setCodigoHttp(200);
			respuesta.setCuerpo(objeto);
			respuesta.setEstado(true);
			respuesta.setMensaje("creado");
			return respuesta;
		}
		respuesta.setMensaje("EL usuario no tiene permitido crear comentarios que no son suyos");
		return respuesta;
	}

	public Respuesta<T> actualizarPorUsuario(OAuth2Authentication auth, final T objeto) {
		logger.info("CLASE ===>" + objeto.getClass().getName());
		String usuario = validaUsuario(objeto);

		final Respuesta<T> respuesta = new Respuesta<T>();
		respuesta.setCodigo(400);
		respuesta.setCodigoHttp(400);
		respuesta.setCuerpo(objeto);
		respuesta.setEstado(false);

		if (usuario == null) { // SI ENCONTRO EL USUARIO DENTRO DEL OBJETO
			respuesta.setMensaje("EL usuario no existe o no tiene mapeada la clase de usuario publico");
			return respuesta;
		}

		if (usuario.equals(auth.getPrincipal().toString())) { // SI SON LOS MISMOS
			entityManager.merge(objeto);
			entityManager.flush();
			respuesta.setCodigo(200);
			respuesta.setCodigoHttp(200);
			respuesta.setCuerpo(objeto);
			respuesta.setEstado(true);
			respuesta.setMensaje("actualizado");
			return respuesta;
		}
		respuesta.setMensaje("EL usuario no tiene permitido actualizar comentarios que no son suyos");
		return respuesta;
	}

	public Respuesta<Boolean> eliminarPorUsuario(OAuth2Authentication auth, final S id) {
		T objeto = entityManager.find(clazz, id);
		
		logger.info("CLASE ===>" + objeto.getClass().getName());
		String usuario = validaUsuario(objeto);

		final Respuesta<Boolean> respuesta = new Respuesta<Boolean>();
		respuesta.setCodigo(400);
		respuesta.setCodigoHttp(400);
		respuesta.setCuerpo(false);
		respuesta.setEstado(false);

		if (usuario == null) { // SI ENCONTRO EL USUARIO DENTRO DEL OBJETO
			respuesta.setMensaje("EL usuario no existe o no tiene mapeada la clase de usuario publico");
			return respuesta;
		}

		if (usuario.equals(auth.getPrincipal().toString())) { // SI SON LOS MISMOS
			entityManager.remove(objeto);
			respuesta.setCodigo(200);
			respuesta.setCodigoHttp(200);
			respuesta.setCuerpo(true);
			respuesta.setEstado(true);
			respuesta.setMensaje("eliminado");
			return respuesta;
		}
		respuesta.setMensaje("EL usuario no tiene permitido eliminar comentarios que no son suyos");
		return respuesta;
	}

	private String validaUsuario(T objeto) {
		String usuario = null;
		try {
			for (Field field : objeto.getClass().getDeclaredFields()) {
				if (field.getType().equals(UsuarioPublico.class)) {
					Field usuarioPublicoField = null;
					Field usernameField = null;
					try {
						usuarioPublicoField = objeto.getClass().getDeclaredField(field.getName());
						if (usuarioPublicoField != null) {
							usuarioPublicoField.setAccessible(true);
							UsuarioPublico usuarioPublico2 = (UsuarioPublico) usuarioPublicoField.get(objeto);
							if (usuarioPublico2 != null) {
								usernameField = usuarioPublico2.getClass().getDeclaredField("username");
								if (usernameField != null) {
									usernameField.setAccessible(true);
									usuario = (String) usernameField.get(usuarioPublico2);
									logger.info("username ===>" + usuario);
									usernameField.setAccessible(false);
								}
							}
							usuarioPublicoField.setAccessible(false);
						}
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException
							| IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return usuario;
	}


}
