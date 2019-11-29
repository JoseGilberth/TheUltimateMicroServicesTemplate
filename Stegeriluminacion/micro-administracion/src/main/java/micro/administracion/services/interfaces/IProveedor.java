package micro.administracion.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dto.main.Respuesta;
import dto.micro.administracion.proveedor.FiltroProveedorDTO;
import modelo.proveedor.Proveedor;

public interface IProveedor {
	
	Respuesta<Page<Proveedor>> filtrar(Pageable pageable, FiltroProveedorDTO filtroProveedorDTO);

	
}
