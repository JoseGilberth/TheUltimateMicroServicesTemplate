package micro.admin.modules.dashboard.interfaces;

import java.sql.SQLException;

import dto.main.Respuesta;
import micro.admin.modules.dashboard.dtos.MetricsDTO;

public interface IMetricsDataBaseServices {
 
	Respuesta<MetricsDTO> metrics() throws SQLException;
	
}
