package micro.auth.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.auth.FiltroLogDTO;
import dto.main.Respuesta;
import model.auth.log.LogRequest;

public interface ILog {

	Respuesta<Page<LogRequest>> filtrar(Pageable pageable, FiltroLogDTO filtroLogDTO);

}
