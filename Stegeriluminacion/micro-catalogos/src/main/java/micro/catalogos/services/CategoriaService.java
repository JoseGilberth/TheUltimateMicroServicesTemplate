package micro.catalogos.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CategoriaDao;
import interfaces.ACrud;
import micro.catalogos.services.interfaces.ICategoriaService;
import modelo.producto.Categoria;

@Service
public class CategoriaService extends ACrud<Categoria, Long> implements ICategoriaService {

	Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	CategoriaDao categoriaDao;


	

}
