package micro.usuarios.publico.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import micro.usuarios.publico.services.interfaces.ACrud;
import modelo.auth.usuarios.publicos.PermisoPublico;

@Service
public class PermisosPublicosService extends ACrud<PermisoPublico> {

	Logger logger = LoggerFactory.getLogger(PermisosPublicosService.class);

}
