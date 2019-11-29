package micro.administracion.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;
import dto.micro.administracion.producto.FiltroProductoDTO;
import modelo.producto.Producto;

public interface IProducto {

	Respuesta<Page<Producto>> filtrar(Pageable pageable, FiltroProductoDTO filtroProductoDTO);
	
}
