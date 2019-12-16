package micro.usuarios.publico.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import interfaces.ACrud;
import micro.usuarios.publico.services.interfaces.IPermisoPublico;
import modelo.auth.usuarios.publicos.PermisoPublico;

@Service
public class PermisosPublicosService extends ACrud<PermisoPublico, Long> implements IPermisoPublico {

	Logger logger = LoggerFactory.getLogger(PermisosPublicosService.class);

}
