package micro.usuarios.administradores.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import interfaces.interfaces.MainCrud;
import micro.usuarios.administradores.interfaces.IPermisoAdministrador;
import model.auth.usuarios.administradores.PermisoAdministrador;

 
@Service
public class PermisosAdminService extends MainCrud<PermisoAdministrador, Long> implements IPermisoAdministrador {

	Logger logger = LoggerFactory.getLogger(this.getClass());
}
