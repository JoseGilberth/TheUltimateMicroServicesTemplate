package interfaces.publico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import dto.main.Respuesta; 

public interface IFiltroEndPoints<S,T> {

	public ResponseEntity<Respuesta<Page<S>>> filtrar(Pageable pageable, @RequestBody T t);
	
	

}
