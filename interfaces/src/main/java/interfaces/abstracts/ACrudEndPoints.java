package interfaces.abstracts;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import utils.StaticVariables;

public abstract class ACrudEndPoints<T> {

	@GetMapping(value = "/heartbeat", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> heartBit() {
		return new ResponseEntity<String>(StaticVariables.OK, HttpStatus.OK);
	}

}
