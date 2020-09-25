package micro.auth.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.auth.FiltroLogDTO;
import dto.main.Respuesta;
import micro.auth.services.LogService;
import model.auth.log.LogRequest;

@RestController
@RequestMapping(path = "/log")
public class LogController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	LogService logService;

	@PostMapping(value = "/filtro", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Respuesta<Page<LogRequest>>> filtrar(Pageable pageable,
			@RequestBody FiltroLogDTO filtroLogDTO) {
		Respuesta<Page<LogRequest>> respuesta = logService.filtrar(pageable, filtroLogDTO);
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
