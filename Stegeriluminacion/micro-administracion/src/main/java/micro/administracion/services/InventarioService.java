package micro.administracion.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dao.InventarioDao;
import dto.main.Respuesta;
import dto.micro.administracion.inventario.FiltroInventarioDTO;
import interfaces.ACrud;
import micro.administracion._config.languaje.Translator;
import micro.administracion.services.interfaces.IInventario;
import modelo.inventario.Inventario;

@Service
public class InventarioService extends ACrud<Inventario, Long> implements IInventario {

	Logger logger = LoggerFactory.getLogger(InventarioService.class);

	@Autowired
	InventarioDao inventarioDao;
	
	
	public Respuesta<Inventario> obtenerPorIdProducto(Long idProducto) {
		Respuesta<Inventario> respuesta = new Respuesta<Inventario>(); 
		Inventario inventario = inventarioDao.obtenerPorIdProducto(idProducto); 
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(inventario);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("inventario.obtenido"));
		return respuesta;
	}


	@Override
	public Respuesta<Page<Inventario>> filtrar(Pageable pageable, FiltroInventarioDTO filtroInventarioDTO) {
		Respuesta<Page<Inventario>> respuesta = new Respuesta<Page<Inventario>>();
		Page<Inventario> inventarios = inventarioDao.obtenerTodosPorPaginacion(pageable);
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(inventarios);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("inventario.obtenido"));
		return respuesta;
	}

}
