package interfaces.publico;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import dto.main.Respuesta; 

public interface ICRUDEndPoints<T,S> {
 
	public ResponseEntity<Respuesta<T>> obtener(@PathVariable("id") S id);
	
	public ResponseEntity<Respuesta<T>> crear( OAuth2Authentication auth, @RequestBody T t);
	
 
	public ResponseEntity<Respuesta<T>> actualizar(OAuth2Authentication auth , @RequestBody T t);
	
	public ResponseEntity<Respuesta<Boolean>> borrar(OAuth2Authentication auth , @PathVariable("id") S id );

}
