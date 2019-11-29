package micro.usuarios.publico.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.Respuesta;
import micro.usuarios.publico.services.RegistroService;
import modelo.auth.usuarios.publicos.UsuarioPublico;


@RestController
@RequestMapping(path = "/usuarios/publico/registro")
public class RegistroController {

	Logger logger = LoggerFactory.getLogger( RegistroController.class );
	
	@Autowired
	RegistroService registroService;
	

	@GetMapping( value="/heartbit", produces= { MediaType.APPLICATION_JSON_UTF8_VALUE , MediaType.APPLICATION_XML_VALUE } )
	public ResponseEntity<String>  heartBit() { 
		return new ResponseEntity<String>( "OK" , HttpStatus.OK );
	}

	
	@PostMapping ( 
	    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
	  , produces= { MediaType.APPLICATION_JSON_UTF8_VALUE , MediaType.APPLICATION_XML_VALUE }
	)
	public ResponseEntity<Respuesta<UsuarioPublico>> registrar( @RequestBody UsuarioPublico usuarioPublico) {
		usuarioPublico.setCorreo(usuarioPublico.getCorreo().replaceAll("\\s",""));
		Respuesta<UsuarioPublico> respuesta = registroService.crearRegistro( usuarioPublico );
		return ResponseEntity.status(respuesta.getCodigoHttp()).body( respuesta );  
	}
	


	@GetMapping(value = "/activar/{token}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> activarUsuario(@PathVariable("token") String token) {
		Respuesta<String> respuesta = null;
		try {
			respuesta = registroService.activarUsuario( token ); 
		    return ResponseEntity.status(respuesta.getCodigoHttp()).body( respuesta.getCuerpo() );   
		}catch (Exception ex) {
		    return ResponseEntity.status(500).body( "" ); 
		} 
	}

	
	
	
	
	 
}
