package micro.administracion.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dao.InventarioDao;
import dao.ProductoDao;
import dto.main.Respuesta;
import dto.micro.administracion.producto.CrearProductoDTO;
import dto.micro.administracion.producto.FiltroProductoDTO;
import interfaces.ACrud;
import micro.administracion._config.languaje.Translator;
import micro.administracion.services.interfaces.IProducto;
import modelo.producto.Producto;

@Service
public class ProductoService extends ACrud<Producto, Long> implements IProducto {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProductoDao productoDao;
	
	@Autowired
	InventarioDao inventarioDao;

	@Override
	public Respuesta<Page<Producto>> filtrar(Pageable pageable, FiltroProductoDTO filtroProductoDTO) {
		Respuesta<Page<Producto>> respuesta = new Respuesta<Page<Producto>>();
		Page<Producto> productos = productoDao.obtenerTodosPorPaginacion(pageable, filtroProductoDTO.isActivo());
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(productos);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("producto.obtenido"));
		return respuesta;
	}

	
	
	public Respuesta<Producto> crearProductoInventario(CrearProductoDTO crearProductoDTO) {
		Respuesta<Producto> respuesta = new Respuesta<Producto>();
	
		Producto producto = productoDao.saveAndFlush(crearProductoDTO.getProducto());

		crearProductoDTO.getInventario().setProducto(producto);
		inventarioDao.saveAndFlush(crearProductoDTO.getInventario());
		 
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(producto);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("producto.creado"));
		return respuesta;
	}

}
