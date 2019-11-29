package micro.auth.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;
import dto.micro.auth.FiltroLogDTO;
import modelo.auth.log.Log;

public interface ILog {

	Respuesta<Page<Log>> filtrar(Pageable pageable, FiltroLogDTO filtroLogDTO);
	 
	
}
