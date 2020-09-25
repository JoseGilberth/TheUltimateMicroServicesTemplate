package interfaces.interfaces;

import java.util.List;

import dto.main.Respuesta;

public interface ICrud<C, I> {

	Respuesta<C> obtenerPorId(I id);

	Respuesta<List<C>> obtenerTodos();

	Respuesta<C> crear(C toCreate);

	Respuesta<List<Respuesta<C>>> crear(Iterable<C> toCreate);

	Respuesta<List<Respuesta<C>>> crear(Iterable<C> toCreate, int batchSize);

	Respuesta<C> actualizar(I id, C toUpdate);

	Respuesta<C> actualizar(C toUpdate);

	Respuesta<Boolean> borrar(I id);

}
