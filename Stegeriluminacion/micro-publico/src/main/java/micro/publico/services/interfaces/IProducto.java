package micro.publico.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;
import modelo.producto.Producto;
import dto.micro.publico.producto.FiltroProductoDTO;

public interface IProducto {

	Respuesta<Page<Producto>> obtenerTodos(Pageable pageable);
	
	Respuesta<Page<Producto>> obtenerTodosPersonalizado(Pageable pageable,FiltroProductoDTO filtroProductoDTO);
	
	
	
}
