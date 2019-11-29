package micro.publico.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dto.main.Respuesta;
import interfaces.ACrud;
import micro.publico._config.languaje.Translator;
import micro.publico.services.interfaces.IPregunta;
import modelo.Pregunta;
import steger.excepciones.controladas.ErrorInternoControlado;
import dao.PreguntaDao;

@Service
public class PreguntaService extends ACrud<Pregunta, Long> implements IPregunta {
	
	Logger logger = LoggerFactory.getLogger( PreguntaService.class );
	
	@Autowired
	PreguntaDao preguntDao;
	
	@Override
	public Respuesta<Page<Pregunta>> obtenerTodos(Pageable pageable) {
		Respuesta<Page<Pregunta>> respuesta = new Respuesta<Page<Pregunta>>();
		try {
			Page<Pregunta> preguntas = preguntDao.obtenerTodosPorPaginacion(pageable);
			respuesta.setCodigo( 200 );
			respuesta.setCodigoHttp( 200 );
			respuesta.setCuerpo(preguntas);
			respuesta.setEstado( true );
			respuesta.setMensaje( Translator.toLocale("preguntas.obtenertodas") );
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	@Override
	public Respuesta<Boolean> borrarByUser(int idUsuario, Long idPregunta) {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>(); 
		try {
			boolean preguntas = false; //preguntDao.borrarByUsuario(idUsuario, idPregunta);
			respuesta.setCodigo( 200 );
			respuesta.setCodigoHttp( 200 );
			respuesta.setCuerpo(preguntas);
			respuesta.setEstado( true );
			respuesta.setMensaje( Translator.toLocale("preguntas.borrado") );
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}
	
	

}
