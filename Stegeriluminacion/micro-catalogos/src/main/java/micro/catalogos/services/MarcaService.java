package micro.catalogos.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import interfaces.ACrud;
import micro.catalogos.services.interfaces.IMarcaService;
import modelo.producto.Marca;

@Service
public class MarcaService extends ACrud<Marca, Long> implements IMarcaService {

	Logger logger = LoggerFactory.getLogger( this.getClass() );
 
	

}
