package micro.admin.modules.dashboard.controllers;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import abstracts.ACRUDEndPoints;
import dto.main.Respuesta;
import micro.admin.modules.dashboard.dtos.MetricsDTO;
import micro.admin.modules.dashboard.interfaces.IMetricsDataBaseServices;

@RestController
@RequestMapping(path = "/database")
public class MetricsDataBaseController extends ACRUDEndPoints<MetricsDataBaseController> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IMetricsDataBaseServices iDashboardServices;
	
	@GetMapping(value = "/metrics", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Respuesta<MetricsDTO>> listAll() throws SQLException {
		Respuesta<MetricsDTO> respuesta = iDashboardServices.metrics();
		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
