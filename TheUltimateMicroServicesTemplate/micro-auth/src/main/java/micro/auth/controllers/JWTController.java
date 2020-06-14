package micro.auth.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping( "/jwt" )
public class JWTController {

	Logger logger = LoggerFactory.getLogger(JWTController.class);

	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@GetMapping( 
			produces= { MediaType.APPLICATION_JSON_UTF8_VALUE , MediaType.APPLICATION_XML_VALUE } 
	)
	public ResponseEntity<OAuth2Authentication> obtenerPorId( OAuth2Authentication auth ) {
		try {
			logger.info("JWTController");
			logger.info( auth.getName() );
			return ResponseEntity.ok( auth );
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<OAuth2Authentication>( new OAuth2Authentication(null, null) , HttpStatus.INTERNAL_SERVER_ERROR );
	}
	

	@GetMapping(  value="/password/{password}"  )
	public ResponseEntity<String> validarPassword( @PathVariable("password") String password ) {
		try {  
			logger.info("/password/{password} : " + password); 
			String contra = bcrypt.encode(password);
			return ResponseEntity.ok(contra);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<String>( "" , HttpStatus.INTERNAL_SERVER_ERROR );
	}
	
	

	
}
