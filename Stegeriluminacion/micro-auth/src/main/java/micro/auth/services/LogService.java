package micro.auth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dao.auth.LogDao;
import dto.main.Respuesta;
import dto.micro.auth.FiltroLogDTO;
import micro.auth._config.languaje.Translator;
import micro.auth.services.interfaces.ACrud;
import micro.auth.services.interfaces.ILog;
import modelo.auth.log.Log;

@Service
public class LogService extends ACrud<Log> implements ILog {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	LogDao logDao;

	@Override
	public Respuesta<Page<Log>> filtrar(Pageable pageable, FiltroLogDTO filtroLogDTO) {
		Respuesta<Page<Log>> respuesta = new Respuesta<Page<Log>>();
		Page<Log> sesiones = logDao.obtenerTodosPorPaginacion(pageable, filtroLogDTO);
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(sesiones);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("proveedor.obtenido"));
		return respuesta;
	}

}
