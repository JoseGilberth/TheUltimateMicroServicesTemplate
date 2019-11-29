package micro.usuarios.publico.services.interfaces;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import dto.main.Respuesta;
import steger.excepciones.controladas.ErrorInternoControlado;

@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public abstract class ACrud<T extends Serializable> {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	private Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
			.getActualTypeArguments()[0];

	Logger logger = LoggerFactory.getLogger(clazz);

	public Respuesta<T> obtener(final Long id) {
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

	public Respuesta<Boolean> eliminar(final long id) {
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

}
