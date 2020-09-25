package micro.auth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dao.auth.LogRequestDao;
import dto.auth.FiltroLogDTO;
import dto.main.Respuesta;
import interfaces.interfaces.MainCrud;
import micro.auth.interfaces.ILog;
import model.auth.log.LogRequest;
import utils._config.language.Translator;

@Service
public class LogService extends MainCrud<LogRequest, Long> implements ILog {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	LogRequestDao logDao;

	@Override
	public Respuesta<Page<LogRequest>> filtrar(Pageable pageable, FiltroLogDTO filtroLogDTO) {
		Page<LogRequest> sesiones = logDao.filtro(pageable, filtroLogDTO);
		return new Respuesta<Page<LogRequest>>(200, sesiones, Translator.toLocale("auth.logservice.filtrar"));
	}

}
