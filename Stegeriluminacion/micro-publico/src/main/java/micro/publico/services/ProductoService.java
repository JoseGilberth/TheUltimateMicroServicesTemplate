package micro.publico.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dao.ProductoDao;
import dto.main.Respuesta;
import dto.micro.publico.producto.FiltroProductoDTO;
import  steger.excepciones.controladas.ErrorInternoControlado;
import interfaces.ACrud;
import micro.publico._config.languaje.Translator;
import micro.publico.services.interfaces.IProducto;
import modelo.producto.Producto;

@Service
public class ProductoService extends ACrud<Producto, UUID> implements IProducto {

	Logger logger = LoggerFactory.getLogger( ProductoService.class );
	
	@Autowired
	ProductoDao productoDao;
	
	@Override
	public Respuesta<Page<Producto>>obtenerTodos(Pageable pageable) {
		Respuesta<Page<Producto>> respuesta = new Respuesta<Page<Producto>>();
		try {
			Page<Producto> productos = productoDao.obtenerTodosPersonalizadoPorPaginacion( pageable );
			respuesta.setCodigo( 200 );
			respuesta.setCodigoHttp( 200 );
			respuesta.setCuerpo(productos);
			respuesta.setEstado( true );
			respuesta.setMensaje( Translator.toLocale("producto.obtenertodos") );
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	

	@Override
	public Respuesta<Page<Producto>> obtenerTodosPersonalizado(Pageable pageable, FiltroProductoDTO filtroProductoDTO) {
		Respuesta<Page<Producto>> respuesta = new Respuesta<Page<Producto>>();
		try {
			Page<Producto> productos = productoDao.obtenerTodosPersonalizadoPorPaginacion(pageable);
			respuesta.setCodigo( 200 );
			respuesta.setCodigoHttp( 200 );
			respuesta.setCuerpo(productos);
			respuesta.setEstado( true );
			respuesta.setMensaje( Translator.toLocale("producto.obtenertodos") );
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}
	

}
