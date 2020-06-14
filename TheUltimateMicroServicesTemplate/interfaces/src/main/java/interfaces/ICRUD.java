package interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;

public interface ICRUD<C, F, I> {

	Respuesta<Page<C>> filtrar(Pageable pageable, F filtro);

	Respuesta<C> crear(C toCreate);

	Respuesta<C> actualizar(I id, C toUpdate);

	Respuesta<Boolean> borrar(I id);

}
