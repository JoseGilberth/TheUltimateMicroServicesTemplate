package micro.administracion.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dao.ProveedorDao;
import dto.main.Respuesta;
import dto.micro.administracion.proveedor.FiltroProveedorDTO;
import interfaces.ACrud;
import micro.administracion._config.languaje.Translator;
import micro.administracion.services.interfaces.IProveedor;
import modelo.proveedor.Proveedor;

@Service
public class ProveedorService extends ACrud<Proveedor, Long> implements IProveedor {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProveedorDao proveedorDao;

	@Override
	public Respuesta<Page<Proveedor>> filtrar(Pageable pageable, FiltroProveedorDTO filtroProveedorDTO) {
		Respuesta<Page<Proveedor>> respuesta = new Respuesta<Page<Proveedor>>();
		Page<Proveedor> proveedores = proveedorDao.obtenerTodosPorPaginacion(pageable, filtroProveedorDTO.isActivo());
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(proveedores);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("proveedor.obtenido"));
		return respuesta;
	}

}
