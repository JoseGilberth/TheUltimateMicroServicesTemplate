package micro.usuarios.catalogos.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.Respuesta;
import micro.usuarios.catalogos.services.TimeUnitService;

@RestController
@RequestMapping(path = "/catalogos")
public class TimeUnitController {

	Logger logger = LoggerFactory.getLogger(TimeUnitController.class);

	@Autowired
	TimeUnitService timeUnitService;

	@GetMapping(value = "/heartbit", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> heartBit() {
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@GetMapping(value = "/timeunits", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Respuesta<List<TimeUnit>>> listAll() {
		Respuesta<List<TimeUnit>> respuesta = null;
		respuesta = timeUnitService.listAll();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
