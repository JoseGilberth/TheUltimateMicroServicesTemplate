package micro.publico.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dao.RespuestaDao;
import interfaces.ACrud;
import micro.publico._config.languaje.Translator;
import micro.publico.services.interfaces.IRespuesta;
import steger.excepciones.controladas.ErrorInternoControlado;
import dto.main.Respuesta;
@Service
public class RespuestaService extends ACrud<modelo.Respuesta, Long> implements IRespuesta {
	
	Logger logger = LoggerFactory.getLogger( RespuestaService.class );
	
	@Autowired
	RespuestaDao respDao;

	@Override
	public Respuesta<Page<modelo.Respuesta>> obtenerTodos(Pageable pageable) {
		Respuesta<Page<modelo.Respuesta>> respuestas = new Respuesta<Page<modelo.Respuesta>>();
		
		try {
			Page<modelo.Respuesta> respuestaAll = respDao.obtenerTodosPorPaginacion( pageable );
			respuestas.setCodigo( 200 );
			respuestas.setCodigoHttp( 200 );
			respuestas.setCuerpo(respuestaAll);
			respuestas.setEstado( true );
			respuestas.setMensaje( Translator.toLocale("Respuestas.obtenertodos") );
			return respuestas;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	@Override
	public Respuesta<Boolean> borrarByUser(int idUsuario, Long idRespuesta) {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>(); 
		try {
			boolean respuestaAll = false; //respDao.borrarByUsuario(idUsuario, idRespuesta);
			respuesta.setCodigo( 200 );
			respuesta.setCodigoHttp( 200 );
			respuesta.setCuerpo(respuestaAll);
			respuesta.setEstado( true );
			respuesta.setMensaje( Translator.toLocale("Respuestas.borrada") );
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	


}
