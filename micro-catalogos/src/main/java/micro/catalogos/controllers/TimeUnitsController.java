package micro.catalogos.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.main.Respuesta;
import interfaces.abstracts.ACrudEndPoints;
import micro.catalogos.interfaces.ITimeUnitsService;

@RestController
@RequestMapping(path = "/catalogos/timeunits")
public class TimeUnitsController extends ACrudEndPoints<TimeUnitsController> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ITimeUnitsService iTimeUnitsService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<String[]>> listAll() {
		Respuesta<String[]> respuesta = iTimeUnitsService.listAll();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
