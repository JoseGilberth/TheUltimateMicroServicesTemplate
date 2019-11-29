package interfaces.administracion;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import dto.main.Respuesta; 

public interface ICRUDEndPoints<T,S> {
 
	public ResponseEntity<Respuesta<T>> obtener(@PathVariable("id") S id);
	
	public ResponseEntity<Respuesta<List<T>>> obtenerTodos();

	public ResponseEntity<Respuesta<T>> crear(@RequestBody T t);

	public ResponseEntity<Respuesta<T>> actualizar(@RequestBody T t);
	
	public ResponseEntity<Respuesta<Boolean>> borrar(@PathVariable("id") S id );

}
