package interfaces.interfaces;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import dto.main.Respuesta;
import utils._config.language.Translator;

@Transactional
public class MainCrud<C, I> implements ICrud<C, I> {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	private Class<C> clazz = (Class<C>) ((ParameterizedType) getClass().getGenericSuperclass())
			.getActualTypeArguments()[0];

	Logger logger = LoggerFactory.getLogger(clazz);

	@Override
	public Respuesta<C> obtenerPorId(final I id) {
		C objeto = entityManager.find(clazz, id);
		return new Respuesta<C>(200, objeto,
				Translator.toLocale("main.crud.byid", new String[] { clazz.getSimpleName(), String.valueOf(id) }));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Respuesta<List<C>> obtenerTodos() {
		List<C> todos = entityManager.createQuery("from " + clazz.getSimpleName()).getResultList();
		return new Respuesta<List<C>>(200, todos,
				Translator.toLocale("main.crud.findAll", new String[] { clazz.getSimpleName() }));
	}

	@Override
	public Respuesta<C> crear(C toCreate) {
		entityManager.persist(toCreate);
		entityManager.flush();
		return new Respuesta<C>(200, toCreate,
				Translator.toLocale("main.crud.create", new String[] { clazz.getSimpleName() }));
	}

	@Override
	public Respuesta<List<Respuesta<C>>> crear(Iterable<C> toCreate) {
		List<Respuesta<C>> a = new ArrayList<Respuesta<C>>();
		for (C c : toCreate) {
			if (getId(c) == null) {
				a.add(actualizar(c));
			} else {
				a.add(crear(c));
			}
		}
		return new Respuesta<List<Respuesta<C>>>(200, a,
				Translator.toLocale("main.crud.create.all", new String[] { clazz.getSimpleName() }));
	}

	@Override
	public Respuesta<List<Respuesta<C>>> crear(Iterable<C> toCreate, int batchSize) {
		List<Respuesta<C>> a = new ArrayList<Respuesta<C>>();
		Session session = entityManager.unwrap(Session.class);
		session.setJdbcBatchSize(batchSize);
		int i = 0;
		Iterator<C> iterador = toCreate.iterator();
		while (iterador.hasNext()) {
			C objeto = iterador.next();
			session.save(objeto);
			i++;
			if (i % batchSize == 0) {
				session.flush();
				session.clear();
			}
			a.add(new Respuesta<C>(200, objeto,
					Translator.toLocale("main.crud.create.batch", new String[] { clazz.getSimpleName() })));
		}
		return new Respuesta<List<Respuesta<C>>>(200, a,
				Translator.toLocale("main.crud.create.batch.all", new String[] { clazz.getSimpleName() }));
	}

	@Override
	@Modifying
	public Respuesta<C> actualizar(I id, C toUpdate) {
		if (id == getId(toUpdate)) {
			return actualizar(toUpdate);
		} else {
			return new Respuesta<C>(500, toUpdate, Translator.toLocale("main.crud.update.mistmatch",
					new String[] { clazz.getSimpleName(), String.valueOf(id) }));
		}
	}

	@Override
	@Modifying
	public Respuesta<C> actualizar(C toUpdate) {
		entityManager.merge(toUpdate);
		entityManager.flush();
		return new Respuesta<C>(200, toUpdate,
				Translator.toLocale("main.crud.update", new String[] { clazz.getSimpleName() }));
	}

	@Override
	@Modifying
	public Respuesta<Boolean> borrar(I id) {
		C objeto = entityManager.find(clazz, id);
		entityManager.remove(objeto);
		return new Respuesta<Boolean>(200, true,
				Translator.toLocale("main.crud.delete", new String[] { clazz.getSimpleName(), String.valueOf(id) }));
	}

	/*
	 * ID
	 */
	private Long getId(Object class1) {
		DbRefFieldCallback callback = new DbRefFieldCallback(class1);
		ReflectionUtils.doWithFields(class1.getClass(), callback);
		return callback.getId();
	}

	private static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {
		private Long id = null;
		private Object object;

		public DbRefFieldCallback(Object object) {
			super();
			this.object = object;
		}

		@Override
		public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
			ReflectionUtils.makeAccessible(field);
			if (field.isAnnotationPresent(Id.class)) {
				id = ((Long) field.get(object));
			}
		}

		public Long getId() {
			return id;
		}
	}
}
