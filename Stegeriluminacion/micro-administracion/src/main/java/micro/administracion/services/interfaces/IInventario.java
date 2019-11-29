package micro.administracion.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;
import dto.micro.administracion.inventario.FiltroInventarioDTO;
import modelo.inventario.Inventario;

public interface IInventario {

	Respuesta<Page<Inventario>> filtrar(Pageable pageable, FiltroInventarioDTO filtroInventarioDTO);
	 
	
}
