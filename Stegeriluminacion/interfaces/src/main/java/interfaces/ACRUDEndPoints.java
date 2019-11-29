package interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public abstract class ACRUDEndPoints<T> {

	@GetMapping(value = "/heartbit", produces = { "application/text" })
	public ResponseEntity<String> heartBit() {
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	} 

}
