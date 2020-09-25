package micro.usuarios.publicos.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import interfaces.interfaces.MainCrud;
import micro.usuarios.publicos.interfaces.IPermisoPublico;
import model.auth.usuarios.publicos.PermisoPublico;

@Service
public class PermisosPublicosService extends MainCrud<PermisoPublico, Long> implements IPermisoPublico {

	Logger logger = LoggerFactory.getLogger(this.getClass());

}
