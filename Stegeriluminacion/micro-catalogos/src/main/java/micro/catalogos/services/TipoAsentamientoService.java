package micro.catalogos.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import interfaces.ACrud;
import micro.catalogos.services.interfaces.IMarcaService;
import modelo.auth.usuarios.publicos.ubicacion.TipoAsentamiento; 

@Service
public class TipoAsentamientoService extends ACrud<TipoAsentamiento, Long> implements IMarcaService {

	Logger logger = LoggerFactory.getLogger( this.getClass() );
 
	

}
