package micro.publico.services.interfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import dto.main.Respuesta;
import dto.micro.publico.solicitudes.FiltroSolicitudDTO;
import modelo.solicitud.compra.SolicitudDeCompra;

public interface ISolicitudDeCompra {
	Respuesta<Page<SolicitudDeCompra>> obtenerSolicitudesTodas(Pageable pageable);
	
	Respuesta<Page<SolicitudDeCompra>> obtenerSolicitudesPersonalizada(Pageable pageable, FiltroSolicitudDTO filtroSolicitudDTO);
	
	Respuesta<Boolean> borrarByUser(int idUsuario, Long idSolicitud);
}
