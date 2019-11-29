package micro.publico.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import dto.main.Respuesta;
import dto.micro.publico.solicitudes.FiltroSolicitudDTO;
import interfaces.ACrud;
import micro.publico._config.languaje.Translator;
import micro.publico.services.interfaces.ISolicitudDeCompra;
import modelo.solicitud.compra.SolicitudDeCompra;
import steger.excepciones.controladas.ErrorInternoControlado;
import dao.SolicitudCompraDao;

@Service
public class SolicitudDeCompraService  extends ACrud<SolicitudDeCompra, Long> implements ISolicitudDeCompra{
	
	Logger logger = LoggerFactory.getLogger( SolicitudDeCompraService.class );
	
	@Autowired
	SolicitudCompraDao  solicitudDao;
	
	@Override
	public Respuesta<Page<SolicitudDeCompra>> obtenerSolicitudesTodas(Pageable pageable) {
		Respuesta<Page<SolicitudDeCompra>> respuesta = new Respuesta<Page<SolicitudDeCompra>>();
		
		try {
			Page<SolicitudDeCompra> solicitudes = solicitudDao.obtenerTodosPorPaginacion( pageable );
			respuesta.setCodigo( 200 );
			respuesta.setCodigoHttp( 200 );
			respuesta.setCuerpo(solicitudes);
			respuesta.setEstado( true );
			respuesta.setMensaje( Translator.toLocale("solicitudes.obtenertodos") );
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	@Override
	public Respuesta<Page<SolicitudDeCompra>> obtenerSolicitudesPersonalizada(Pageable pageable,
			FiltroSolicitudDTO filtroSolicitudDTO) {
	Respuesta<Page<SolicitudDeCompra>> respuesta = new Respuesta<Page<SolicitudDeCompra>>();
		
		try {
			Page<SolicitudDeCompra> solicitudes = solicitudDao.obtenerTodosPersonalizadoPorPaginacion( pageable );
			respuesta.setCodigo( 200 );
			respuesta.setCodigoHttp( 200 );
			respuesta.setCuerpo(solicitudes);
			respuesta.setEstado( true );
			respuesta.setMensaje( Translator.toLocale("solicitudes.obtenertodos") );
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	@Override
	public Respuesta<Boolean> borrarByUser(int idUsuario, Long idSolicitud) {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>(); 
		try {
			boolean solicitudes = solicitudDao.borrarByUsuario(idUsuario, idSolicitud);
			respuesta.setCodigo( 200 );
			respuesta.setCodigoHttp( 200 );
			respuesta.setCuerpo(solicitudes);
			respuesta.setEstado( true );
			respuesta.setMensaje( Translator.toLocale("solicitud.borrada") );
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	
}
